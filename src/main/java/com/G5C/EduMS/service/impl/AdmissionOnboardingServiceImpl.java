package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.AccountStatus;
import com.G5C.EduMS.common.enums.ApplicationStatus;
import com.G5C.EduMS.common.enums.CohortStatus;
import com.G5C.EduMS.common.enums.StudentStatus;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.model.*;
import com.G5C.EduMS.repository.*;
import com.G5C.EduMS.service.AdmissionOnboardingService;
import com.G5C.EduMS.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdmissionOnboardingServiceImpl implements AdmissionOnboardingService {

    private final AdmissionApplicationRepository applicationRepository;
    private final BenchmarkScoreRepository benchmarkRepository;

    // Các Repo phụ trợ cho việc Onboarding
    private final AccountRepository accountRepository;
    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final AdmissionPeriodRepository admissionPeriodRepository;
    private final GuardianRepository guardianRepository;
    private final CohortRepository cohortRepository;
    private final LecturerRepository lecturerRepository;
    private final AdministrativeClassRepository administrativeClassRepository;
    private final MailService mailService;


    // Dùng để mã hóa mật khẩu mặc định
    private final PasswordEncoder passwordEncoder;

    // =========================================================================
    // 1. TỰ ĐỘNG QUÉT ĐIỂM (AUTO-SCREENING)
    // =========================================================================

    @Override
    @Transactional
    public void autoScreenApplications(Integer periodId) {
        log.info("Bắt đầu chạy Auto-Screening cho Đợt tuyển sinh ID: {}", periodId);

        AdmissionPeriod admissionPeriod = admissionPeriodRepository.findByIdAndDeletedFalse(periodId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Đợt tuyển sinh với ID: " + periodId));

        // 1. Lấy tất cả hồ sơ đang chờ duyệt (PENDING) của đợt này
        List<AdmissionApplication> pendingApps = applicationRepository
                .findAllByAdmissionPeriodIdAndStatusAndDeletedFalse(periodId, ApplicationStatus.PENDING);

        if (pendingApps.isEmpty()) {
            log.info("Không có hồ sơ PENDING nào cần quét.");
            return;
        }

        // 2. Lấy bảng điểm chuẩn của đợt này
        List<BenchmarkScore> benchmarks = benchmarkRepository.findAllByAdmissionPeriodIdAndDeletedFalse(periodId);

        Map<String, Float> benchmarkMap = benchmarks.stream()
                .collect(Collectors.toMap(
                        b -> b.getMajor().getId() + "_" + b.getAdmissionBlock().getId(),
                        BenchmarkScore::getScore
                ));

        int approvedCount = 0;
        int rejectedCount = 0;
        LocalDateTime now = LocalDateTime.now();

        // 3. Tiến hành quét từng hồ sơ
        for (AdmissionApplication app : pendingApps) {

            if (app.getMajor() == null || app.getAdmissionBlock() == null || app.getTotalScore() == null) {
                log.warn("Hồ sơ ID {} bị thiếu Ngành, Khối hoặc Điểm xét tuyển. Bỏ qua Auto-Screening.", app.getId());
                continue;
            }

            String key = app.getMajor().getId() + "_" + app.getAdmissionBlock().getId();
            Float requireScore = benchmarkMap.get(key);

            if (requireScore == null) {
                // Nếu ngành/khối này chưa có điểm chuẩn -> Bỏ qua, để Admin duyệt tay sau
                continue;
            }

            // Logic quyết định số phận: Tổng điểm >= Điểm chuẩn -> Đậu
            if (app.getTotalScore() >= requireScore) {
                app.setStatus(ApplicationStatus.APPROVED);
                approvedCount++;
            } else {
                app.setStatus(ApplicationStatus.REJECTED);
                rejectedCount++;
            }
            app.setApprovalDate(now);
        }

        // 4. Lưu đồng loạt xuống DB
        applicationRepository.saveAll(pendingApps);
        log.info("Auto-Screening hoàn tất! Đậu: {}, Rớt: {}", approvedCount, rejectedCount);
    }

    // =========================================================================
    // 2. CHỐT NHẬP HỌC (ONBOARDING) - TẠO COMBO SINH VIÊN + PHỤ HUYNH
    // =========================================================================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processOnboarding(Integer periodId) {
        log.info("Bắt đầu tự động duyệt hồ sơ và phân lớp cho Đợt ID: {}", periodId);

        // 1. Kiểm tra Đợt tuyển sinh và lấy danh sách hồ sơ hợp lệ
        AdmissionPeriod period = admissionPeriodRepository.findByIdAndDeletedFalse(periodId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy đợt tuyển sinh."));

        if (!"CLOSED".equals(period.getStatus().name())) {
            throw new InvalidDataException("Chỉ có thể chốt nhập học khi Đợt tuyển sinh đã KẾT THÚC (CLOSED). Trạng thái hiện tại: " + period.getStatus());
        }

        List<AdmissionApplication> approvedApps = applicationRepository
                .findAllByAdmissionPeriodIdAndStatusAndDeletedFalse(periodId, ApplicationStatus.APPROVED);

        if (approvedApps.isEmpty()) {
            throw new NotFoundResourcesException("Không có hồ sơ nào trúng tuyển để chốt nhập học.");
        }

        // --- XỬ LÝ RỦI RO 2: UNIQUE CONSTRAINT (Lọc sinh viên đã tồn tại) ---
        Set<String> appNationalIds = approvedApps.stream().map(AdmissionApplication::getNationalId).collect(Collectors.toSet());
        Set<String> appEmails = approvedApps.stream().map(AdmissionApplication::getEmail).collect(Collectors.toSet());

        Set<String> existingNids = studentRepository.findNationalIdsInAndDeletedFalse(appNationalIds);
        Set<String> existingMails = studentRepository.findEmailsInAndDeletedFalse(appEmails);

        List<AdmissionApplication> validApps = approvedApps.stream()
                .filter(app -> !existingNids.contains(app.getNationalId()) && !existingMails.contains(app.getEmail()))
                .collect(Collectors.toList());

        if (validApps.isEmpty()) {
            log.warn("Tất cả hồ sơ trúng tuyển đều đã tồn tại sinh viên trên hệ thống. Bỏ qua tiến trình.");
            return;
        }

        // 2. Tự động xử lý Niên khóa (Cohort)
        int startYear = period.getStartTime().getYear();
        String cohortName = "K" + (startYear % 100);

        Cohort cohort = cohortRepository.findByCohortNameAndDeletedFalse(cohortName)
                .orElseGet(() -> {
                    Cohort newCohort = new Cohort();
                    newCohort.setCohortName(cohortName);
                    newCohort.setStartYear(startYear);
                    newCohort.setEndYear(startYear + 4);
                    newCohort.setStatus(CohortStatus.ACTIVE);
                    newCohort.setDeleted(false);
                    return cohortRepository.save(newCohort);
                });

        // 3. --- XỬ LÝ RỦI RO LECTURER: Tạo/Lấy Giảng viên tạm thời ---
        Role lecturerRole = roleRepository.findByRoleNameAndDeletedFalse("LECTURER")
                .orElseThrow(() -> new NotFoundResourcesException("Chưa cấu hình Role: LECTURER"));

        Lecturer dummyLecturer = lecturerRepository.findByEmailAndDeletedFalse("dummy.lecturer@system.edu.vn")
                .orElseGet(() -> {
                    Account lecAcc = new Account();
                    lecAcc.setUsername("dummy_lecturer");
                    lecAcc.setPassword(passwordEncoder.encode("Dummy@12345"));
                    lecAcc.setRole(lecturerRole);
                    lecAcc.setStatus(AccountStatus.ACTIVE);
                    lecAcc.setCreatedAt(LocalDateTime.now());
                    lecAcc.setDeleted(false);
                    accountRepository.save(lecAcc); // Lưu trước để có ID

                    Lecturer newLec = new Lecturer();
                    newLec.setAccount(lecAcc);
                    newLec.setFullName("Giảng viên Tạm thời");
                    newLec.setEmail("dummy.lecturer@system.edu.vn");
                    newLec.setPhone("0000000000");
                    newLec.setDeleted(false);
                    return lecturerRepository.save(newLec); // Lưu trước để gán cho Lớp
                });

        // Chuẩn bị Role & Cache
        Role studentRole = roleRepository.findByRoleNameAndDeletedFalse("STUDENT")
                .orElseThrow(() -> new NotFoundResourcesException("Chưa cấu hình Role: STUDENT"));
        Role guardianRole = roleRepository.findByRoleNameAndDeletedFalse("GUARDIAN")
                .orElseThrow(() -> new NotFoundResourcesException("Chưa cấu hình Role: GUARDIAN"));

        LocalDateTime now = LocalDateTime.now();
        List<Account> newAccounts = new ArrayList<>();
        List<Guardian> newGuardians = new ArrayList<>();
        List<Student> newStudents = new ArrayList<>();
        Map<String, Guardian> cachedGuardians = new HashMap<>();

        // 4. Gom nhóm thí sinh theo Ngành học & Tự tạo lớp
        Map<Major, List<AdmissionApplication>> appsByMajor = validApps.stream()
                .collect(Collectors.groupingBy(AdmissionApplication::getMajor));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        for (Map.Entry<Major, List<AdmissionApplication>> entry : appsByMajor.entrySet()) {
            Major major = entry.getKey();
            List<AdmissionApplication> apps = entry.getValue();

            // Thuật toán chia đều sĩ số
            int totalStudents = apps.size();
            int numberOfClasses = (int) Math.ceil((double) totalStudents / 50.0);
            int baseStudentsPerClass = totalStudents / numberOfClasses;
            int remainder = totalStudents % numberOfClasses;

            List<AdministrativeClass> classesForMajor = new ArrayList<>();
            for (int i = 1; i <= numberOfClasses; i++) {
                AdministrativeClass newClass = new AdministrativeClass();
                newClass.setClassName(String.format("%s-%s-%02d", major.getMajorCode(), cohortName, i));
                newClass.setCohort(cohort);
                newClass.setMajor(major);
                newClass.setMaxCapacity(50);
                newClass.setHeadLecturer(dummyLecturer); // Gán giảng viên tạm thời thay vì null
                newClass.setDeleted(false);
                classesForMajor.add(newClass);
            }
            administrativeClassRepository.saveAll(classesForMajor);

            int currentClassIndex = 0;
            int assignedCount = 0;
            int currentClassTargetSize = baseStudentsPerClass + (remainder > 0 ? 1 : 0);

            for (AdmissionApplication app : apps) {
                if (assignedCount >= currentClassTargetSize) {
                    currentClassIndex++;
                    assignedCount = 0;
                    remainder--;
                    currentClassTargetSize = baseStudentsPerClass + (remainder > 0 ? 1 : 0);
                }

                AdministrativeClass assignedClass = classesForMajor.get(currentClassIndex);
                assignedCount++;

                // --- XỬ LÝ RỦI RO 1: NullPointerException Ngày sinh ---
                String rawPassword = (app.getDateOfBirth() != null)
                        ? app.getDateOfBirth().format(formatter)
                        : app.getNationalId();
                String encodedPassword = passwordEncoder.encode(rawPassword);

                // --- XỬ LÝ RỦI RO 4: Chuẩn hóa số điện thoại Phụ huynh ---
                String rawPhone = app.getGuardianPhone();
                String parentPhone = (rawPhone != null) ? rawPhone.replaceAll("\\D+", "") : null;
                Guardian guardian = null;

                if (parentPhone != null && !parentPhone.isEmpty()) {
                    guardian = cachedGuardians.get(parentPhone);
                    if (guardian == null) {
                        guardian = guardianRepository.findByPhoneAndDeletedFalse(parentPhone).orElse(null);
                    }

                    if (guardian == null) {
                        String guardianCode = generateUserCode("ph", app.getId());

                        Account guardianAcc = new Account();
                        guardianAcc.setUsername(guardianCode);
                        guardianAcc.setPassword(encodedPassword);
                        guardianAcc.setRole(guardianRole);
                        guardianAcc.setStatus(AccountStatus.ACTIVE);
                        guardianAcc.setCreatedAt(now);
                        guardianAcc.setDeleted(false);
                        newAccounts.add(guardianAcc);

                        guardian = new Guardian();
                        guardian.setAccount(guardianAcc);
                        guardian.setFullName("Phụ huynh của " + app.getFullName());
                        guardian.setPhone(parentPhone);
                        guardian.setDeleted(false);

                        newGuardians.add(guardian);
                        cachedGuardians.put(parentPhone, guardian);
                    }
                }

                // Tạo Profile Sinh viên
                String studentCode = generateUserCode("sv", app.getId());

                Account studentAcc = new Account();
                studentAcc.setUsername(studentCode);
                studentAcc.setPassword(encodedPassword);
                studentAcc.setRole(studentRole);
                studentAcc.setStatus(AccountStatus.ACTIVE);
                studentAcc.setCreatedAt(now);
                studentAcc.setDeleted(false);
                newAccounts.add(studentAcc);

                Student student = new Student();
                student.setAccount(studentAcc);
                student.setAdministrativeClass(assignedClass);
                student.setMajor(major);
                student.setGuardian(guardian);
                student.setStudentCode(studentCode);
                student.setFullName(app.getFullName());
                student.setEmail(app.getEmail());
                student.setPhone(app.getPhone());
                student.setNationalId(app.getNationalId());
                student.setDateOfBirth(app.getDateOfBirth());
                student.setGender(app.getGender());
                student.setAddress(app.getAddress());
                student.setStatus(StudentStatus.ACTIVE);
                student.setCreatedAt(now);
                student.setDeleted(false);

                newStudents.add(student);
                app.setStatus(ApplicationStatus.ENROLLED);
            }
        }

        // --- XỬ LÝ RỦI RO 3: Thứ tự lưu Batch an toàn tránh TransientObjectException ---
        accountRepository.saveAll(newAccounts);
        guardianRepository.saveAll(newGuardians);
        studentRepository.saveAll(newStudents);
        applicationRepository.saveAll(validApps);

        for (Student student : newStudents) {
            mailService.sendAdmissionResult(
                    student.getEmail(),
                    student.getFullName(),
                    student.getStudentCode(),
                    student.getAccount().getUsername(),
                    student.getDateOfBirth().format(formatter)
            );
        }

        log.info("Onboarding hoàn tất! Đã tạo {} sinh viên mới.", newStudents.size());
    }

    private String generateUserCode(String prefix, Integer uniqueId) {
        int currentYear = java.time.Year.now().getValue() % 100;
        return String.format("%s_%02d%05d", prefix.toLowerCase(), currentYear, uniqueId);
    }
}