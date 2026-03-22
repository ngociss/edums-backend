package com.G5C.EduMS.config;

import com.G5C.EduMS.common.enums.AccountStatus;
import com.G5C.EduMS.common.enums.StudentStatus;
import com.G5C.EduMS.model.*;
import com.G5C.EduMS.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;
    private final GuardianRepository guardianRepository;
    private final MajorRepository majorRepository;
    private final AdministrativeClassRepository classRepository;
    private final FacultyRepository facultyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("--- Đang kiểm tra và đồng bộ tài khoản mẫu ---");

        // Băm mật khẩu chuẩn BCrypt
        String encodedPass = passwordEncoder.encode("123456");

        // Đảm bảo có danh mục (Vượt qua NOT NULL)
        Faculty faculty = facultyRepository.findAll().stream().findFirst()
                .orElseGet(() -> facultyRepository.save(Faculty.builder()
                        .facultyName("Faculty of IT").facultyCode("FIT").deleted(false).build()));

        Major major = majorRepository.findAll().stream().findFirst()
                .orElseGet(() -> majorRepository.save(Major.builder()
                        .majorName("Computer Science").majorCode("CS").faculty(faculty).deleted(false).build()));

        AdministrativeClass adminClass = classRepository.findAll().stream().findFirst()
                .orElseGet(() -> classRepository.save(AdministrativeClass.builder()
                        .className("SE1701").major(major).deleted(false).build()));

        // Chạy logic Upsert (Update or Insert)
        upsertAdmin(encodedPass);
        upsertLecturer(encodedPass);
        upsertGuardian(encodedPass);
        upsertStudent(encodedPass, major, adminClass);

        log.info("--- Hoàn tất! Hãy đăng nhập bằng mật khẩu: 123456 ---");
    }

    private void upsertAdmin(String pass) {
        accountRepository.findByUsernameAndDeletedFalse("admin").ifPresentOrElse(
                acc -> {
                    acc.setPassword(pass); // Đè lại pass chuẩn
                    accountRepository.save(acc);
                    log.info(">> Đã CẬP NHẬT mật khẩu cho: {}", "admin");
                },
                () -> {
                    Role role = roleRepository.findByRoleNameAndDeletedFalse("ADMIN").orElseThrow();
                    Account acc = Account.builder().username("admin").password(pass).role(role)
                            .status(AccountStatus.ACTIVE).deleted(false).createdAt(LocalDateTime.now()).build();
                    accountRepository.save(acc);
                    log.info(">> Đã TẠO MỚI Admin: {}", "admin");
                }
        );
    }

    private void upsertLecturer(String pass) {
        accountRepository.findByUsernameAndDeletedFalse("gv_26001").ifPresentOrElse(
                acc -> {
                    acc.setPassword(pass);
                    accountRepository.save(acc);
                    log.info(">> Đã CẬP NHẬT mật khẩu cho: {}", "gv_26001");
                },
                () -> {
                    Role role = roleRepository.findByRoleNameAndDeletedFalse("LECTURER").orElseThrow();
                    Account acc = accountRepository.save(Account.builder().username("gv_26001").password(pass).role(role)
                            .status(AccountStatus.ACTIVE).deleted(false).createdAt(LocalDateTime.now()).build());
                    lecturerRepository.save(Lecturer.builder()
                            .account(acc).fullName("Nguyen Van An").email("gv_26001" + "@edums.edu.vn").deleted(false).build());
                    log.info(">> Đã TẠO MỚI Giảng viên: {}", "gv_26001");
                }
        );
    }

    private void upsertGuardian(String pass) {
        accountRepository.findByUsernameAndDeletedFalse("ph_26001").ifPresentOrElse(
                acc -> {
                    acc.setPassword(pass);
                    accountRepository.save(acc);
                    log.info(">> Đã CẬP NHẬT mật khẩu cho: {}", "ph_26001");
                },
                () -> {
                    Role role = roleRepository.findByRoleNameAndDeletedFalse("GUARDIAN").orElseThrow();
                    Account acc = accountRepository.save(Account.builder().username("ph_26001").password(pass).role(role)
                            .status(AccountStatus.ACTIVE).deleted(false).createdAt(LocalDateTime.now()).build());
                    guardianRepository.save(Guardian.builder()
                            .account(acc).fullName("Le Van Parent").phone("0988000001").deleted(false).build());
                    log.info(">> Đã TẠO MỚI Phụ huynh: {}", "ph_26001");
                }
        );
    }

    private void upsertStudent(String pass, Major major, AdministrativeClass aClass) {
        accountRepository.findByUsernameAndDeletedFalse("sv_26001").ifPresentOrElse(
                acc -> {
                    acc.setPassword(pass);
                    accountRepository.save(acc);
                    log.info(">> Đã CẬP NHẬT mật khẩu cho: {}", "sv_26001");
                },
                () -> {
                    Role role = roleRepository.findByRoleNameAndDeletedFalse("STUDENT").orElseThrow();
                    Account acc = accountRepository.save(Account.builder().username("sv_26001").password(pass).role(role)
                            .status(AccountStatus.ACTIVE).deleted(false).createdAt(LocalDateTime.now()).build());
                    studentRepository.save(Student.builder()
                            .account(acc).studentCode("sv_26001").fullName("Do Minh Khoa").email("sv_26001" + "@sv.edums.edu.vn")
                            .nationalId("012345678901").major(major).administrativeClass(aClass)
                            .status(StudentStatus.ACTIVE).dateOfBirth(LocalDate.of(2005, 2, 14)).deleted(false).build());
                    log.info(">> Đã TẠO MỚI Sinh viên: {}", "sv_26001");
                }
        );
    }
}