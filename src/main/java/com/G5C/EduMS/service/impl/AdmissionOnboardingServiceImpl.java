package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.AccountStatus;
import com.G5C.EduMS.common.enums.ApplicationStatus;
import com.G5C.EduMS.common.enums.StudentStatus;
import com.G5C.EduMS.dto.request.OnboardingRequest;
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
    public void processOnboarding(OnboardingRequest request) {
        log.info("Bắt đầu tiến trình Onboarding cho Đợt ID: {}", request.getPeriodId());

        AdmissionPeriod period = admissionPeriodRepository.findByIdAndDeletedFalse(request.getPeriodId())
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Đợt tuyển sinh với ID: " + request.getPeriodId()));

        List<AdmissionApplication> approvedApps = applicationRepository
                .findAllByAdmissionPeriodIdAndStatusAndDeletedFalse(request.getPeriodId(), ApplicationStatus.APPROVED);

        if (approvedApps.isEmpty()) {
            throw new NotFoundResourcesException("Không có thí sinh nào trúng tuyển để chốt nhập học.");
        }

        // 1. Lấy Roles từ DB
        Role studentRole = roleRepository.findByRoleNameAndDeletedFalse("STUDENT")
                .orElseThrow(() -> new NotFoundResourcesException("Hệ thống chưa cấu hình Role: STUDENT"));
        Role guardianRole = roleRepository.findByRoleNameAndDeletedFalse("GUARDIAN")
                .orElseThrow(() -> new NotFoundResourcesException("Hệ thống chưa cấu hình Role: GUARDIAN"));

        Set<String> nationalIdsToCheck = new HashSet<>();
        Set<String> usernamesToCheck = new HashSet<>();
        Map<Integer, String> cachedStudentCodes = new HashMap<>();
        Map<Integer, String> cachedGuardianCodes = new HashMap<>();

        for (AdmissionApplication app : approvedApps) {
            if (app.getNationalId() != null) {
                nationalIdsToCheck.add(app.getNationalId());
            }
            String studentCode = generateUserCode("sv", app.getId());
            String guardianCode = generateUserCode("ph", app.getId());

            usernamesToCheck.add(studentCode);
            usernamesToCheck.add(guardianCode);

            cachedStudentCodes.put(app.getId(), studentCode);
            cachedGuardianCodes.put(app.getId(), guardianCode);

        }

        Set<String> existingNationalIds = studentRepository
                .findAllByNationalIdInAndDeletedFalse(nationalIdsToCheck)
                .stream()
                .map(Student::getNationalId)
                .collect(Collectors.toSet());

        Set<String> existingUsernames = accountRepository
                .findAllByUsernameIn(usernamesToCheck)
                .stream()
                .map(Account::getUsername)
                .collect(Collectors.toSet());

        List<Student> newStudents = new ArrayList<>();

        List<AdmissionApplication> successfullyProcessedApps = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();

        for (AdmissionApplication app : approvedApps) {

            if (app.getDateOfBirth() == null) {
                throw new InvalidDataException("Hồ sơ ID " + app.getId() + " thiếu Ngày sinh (dùng làm Mật khẩu). Không thể nhập học.");
            }
            if (app.getNationalId() == null || app.getNationalId().trim().isEmpty()) {
                throw new InvalidDataException("Hồ sơ ID " + app.getId() + " thiếu CCCD. Không thể nhập học.");
            }
            if (app.getEmail() == null || app.getEmail().trim().isEmpty()) {
                throw new InvalidDataException("Hồ sơ ID " + app.getId() + " thiếu Email (dùng để gửi thông báo). Không thể nhập học.");
            }
            if (existingNationalIds.contains(app.getNationalId())) {
                log.warn("Thí sinh có CCCD {} đã tồn tại. Bỏ qua.", app.getNationalId());
                continue;
            }

            // Sinh cặp mã định danh đồng bộ (VD: sv_2600150 và ph_2600150)
            String studentCode = cachedStudentCodes.get(app.getId());
            String guardianCode = cachedGuardianCodes.get(app.getId());

            if (existingUsernames.contains(studentCode) || existingUsernames.contains(guardianCode)) {
                log.warn("Tài khoản {} hoặc {} đã tồn tại. Bỏ qua hồ sơ ID {}.", studentCode, guardianCode, app.getId());
                continue;
            }

            String rawPassword = app.getDateOfBirth().toString();
            String encodedPassword = passwordEncoder.encode(rawPassword);

            Account guardianAccount = new Account();
            guardianAccount.setRole(guardianRole);
            guardianAccount.setUsername(guardianCode);
            guardianAccount.setPassword(encodedPassword);
            guardianAccount.setStatus(AccountStatus.ACTIVE);
            guardianAccount.setCreatedAt(now);
            guardianAccount.setDeleted(false);
            Account savedGuardianAccount = accountRepository.save(guardianAccount);

            Guardian guardian = new Guardian();
            guardian.setAccount(savedGuardianAccount);
            guardian.setFullName("Phụ huynh của " + app.getFullName());
            guardian.setPhone(app.getPhone());
            guardian.setRelationship("Phụ huynh");
            guardian.setDeleted(false);
            Guardian savedGuardian = guardianRepository.save(guardian);

            Account studentAccount = new Account();
            studentAccount.setRole(studentRole);
            studentAccount.setUsername(studentCode);
            studentAccount.setPassword(encodedPassword);
            studentAccount.setStatus(AccountStatus.ACTIVE);
            studentAccount.setCreatedAt(now);
            studentAccount.setDeleted(false);
            Account savedStudentAccount = accountRepository.save(studentAccount);

            Student student = new Student();
            student.setAccount(savedStudentAccount);
            student.setGuardian(savedGuardian);
            student.setStudentCode(studentCode);
            student.setMajor(app.getMajor());
            student.setFullName(app.getFullName());
            student.setEmail(app.getEmail());
            student.setPhone(app.getPhone());
            student.setNationalId(app.getNationalId());
            student.setAddress(app.getAddress());
            student.setDateOfBirth(app.getDateOfBirth());
            student.setStatus(StudentStatus.ACTIVE);
            student.setCreatedAt(now);
            student.setDeleted(false);

            newStudents.add(student);

            app.setStatus(ApplicationStatus.ENROLLED);
            successfullyProcessedApps.add(app);
        }

        // Batch save toàn bộ sinh viên
        studentRepository.saveAll(newStudents);
        applicationRepository.saveAll(successfullyProcessedApps);

        for (AdmissionApplication app : successfullyProcessedApps) {
            String studentCode = cachedStudentCodes.get(app.getId());
            String rawPassword = app.getDateOfBirth().toString();

            mailService.sendAdmissionResult(
                    app.getEmail(),
                    app.getFullName(),
                    studentCode,
                    studentCode,
                    rawPassword
            );
        }

        log.info("Onboarding thành công! Đã tạo {} cặp tài khoản Sinh viên & Phụ huynh.", newStudents.size());
    }

    // =========================================================================
    // HÀM TRỢ GIÚP: SINH MÃ ĐỊNH DANH (REUSABLE UTILITY)
    // =========================================================================
    private String generateUserCode(String prefix, Integer uniqueId) {
        int currentYear = java.time.Year.now().getValue() % 100;
        return String.format("%s_%02d%05d", prefix.toLowerCase(), currentYear, uniqueId);
    }
}