package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.ChangePasswordRequest;
import com.G5C.EduMS.dto.request.UpdateProfileRequest;
import com.G5C.EduMS.dto.response.ProfileResponse;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.model.Account;
import com.G5C.EduMS.model.Student;
import com.G5C.EduMS.model.Lecturer;
import com.G5C.EduMS.model.Guardian;
import com.G5C.EduMS.repository.AccountRepository;
import com.G5C.EduMS.repository.StudentRepository;
import com.G5C.EduMS.repository.LecturerRepository;
import com.G5C.EduMS.repository.GuardianRepository;
import com.G5C.EduMS.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final AccountRepository accountRepository;
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;
    private final GuardianRepository guardianRepository;
    private final PasswordEncoder passwordEncoder;

    // =========================================================================
    // 1. XEM THÔNG TIN CÁ NHÂN (HỖ TRỢ TẤT CẢ ROLE)
    // =========================================================================
    @Override
    public ProfileResponse getMyProfile(String username) {
        Account account = getAccountByUsername(username);
        String roleName = account.getRole().getRoleName().toUpperCase();
        return switch (roleName) {
            case "STUDENT" -> {
                Student student = studentRepository.findByAccountIdAndDeletedFalse(account.getId())
                        .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy hồ sơ Sinh viên."));
                yield mapStudentToProfile(account, student);
            }
            case "LECTURER" -> {
                Lecturer lecturer = lecturerRepository.findByAccountIdAndDeletedFalse(account.getId())
                        .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy hồ sơ Giảng viên."));
                yield mapLecturerToProfile(account, lecturer);
            }
            case "GUARDIAN" -> {
                Guardian guardian = guardianRepository.findByAccountIdAndDeletedFalse(account.getId())
                        .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy hồ sơ Phụ huynh."));
                yield mapGuardianToProfile(account, guardian);
            }
            case "ADMIN", "MANAGER" ->
                // Admin thường chỉ cần hiển thị thông tin Account cơ bản
                    ProfileResponse.builder()
                            .username(account.getUsername())
                            .role(roleName)
                            .fullName("Quản trị viên Hệ thống")
                            .build();
            default ->
                    throw new InvalidDataException("Vai trò người dùng (" + roleName + ") không hợp lệ trên hệ thống.");
        };
    }

    // =========================================================================
    // 2. CẬP NHẬT THÔNG TIN CÁ NHÂN (HỖ TRỢ TẤT CẢ ROLE)
    // =========================================================================
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProfileResponse updateMyProfile(String username, UpdateProfileRequest request) {
        Account account = getAccountByUsername(username);
        String roleName = account.getRole().getRoleName().toUpperCase();

        switch (roleName) {
            case "STUDENT":
                Student student = studentRepository.findByAccountIdAndDeletedFalse(account.getId())
                        .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy hồ sơ Sinh viên."));

                updateCommonFields(student, request);
                studentRepository.save(student);

                log.info("Sinh viên [{}] đã cập nhật Profile.", username);
                return mapStudentToProfile(account, student);

            case "LECTURER":
                Lecturer lecturer = lecturerRepository.findByAccountIdAndDeletedFalse(account.getId())
                        .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy hồ sơ Giảng viên."));

                updateCommonFields(lecturer, request);
                lecturerRepository.save(lecturer);

                log.info("Giảng viên [{}] đã cập nhật Profile.", username);
                return mapLecturerToProfile(account, lecturer);

            case "GUARDIAN":
                Guardian guardian = guardianRepository.findByAccountIdAndDeletedFalse(account.getId())
                        .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy hồ sơ Phụ huynh."));

                updateCommonFields(guardian, request);
                guardianRepository.save(guardian);

                log.info("Phụ huynh [{}] đã cập nhật Profile.", username);
                return mapGuardianToProfile(account, guardian);

            case "ADMIN":
            case "MANAGER":
                throw new IllegalStateException("Tài khoản Quản trị không được phép đổi thông tin qua API này.");

            default:
                throw new IllegalArgumentException("Vai trò người dùng không hợp lệ.");
        }
    }

    // =========================================================================
    // 3. ĐỔI MẬT KHẨU (DÙNG CHUNG CHO 100% TÀI KHOẢN)
    // =========================================================================
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(String username, ChangePasswordRequest request) {
        if (request.getOldPassword() == null || request.getNewPassword() == null || request.getConfirmPassword() == null) {
            throw new InvalidDataException("Vui lòng điền đầy đủ các trường mật khẩu!");
        }

        Account account = getAccountByUsername(username);

        if (!passwordEncoder.matches(request.getOldPassword(), account.getPassword())) {
            throw new InvalidDataException("Mật khẩu hiện tại không chính xác!");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InvalidDataException("Mật khẩu xác nhận không trùng khớp!");
        }

        if (passwordEncoder.matches(request.getNewPassword(), account.getPassword())) {
            throw new InvalidDataException("Mật khẩu mới không được trùng với mật khẩu cũ!");
        }

        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        accountRepository.save(account);

        log.info("Tài khoản [{}] đã đổi mật khẩu thành công.", username);
    }

    // =========================================================================
    // CÁC HÀM BỔ TRỢ (UTILITIES & MAPPERS)
    // =========================================================================

    private Account getAccountByUsername(String username) {
        return accountRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy tài khoản."));
    }

    /**
     * Hàm dùng chung để cập nhật các trường thông tin cơ bản cho mọi đối tượng
     */
    private void updateCommonFields(Object entity, UpdateProfileRequest request) {
        if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
            throw new InvalidDataException("Họ và tên không được để trống.");
        }
        String safePhone = request.getPhone() != null ? request.getPhone().trim() : null;
        String safeAddress = request.getAddress() != null ? request.getAddress().trim() : null;
        if (entity instanceof Student s) {
            s.setFullName(request.getFullName().trim());
            s.setPhone(request.getPhone() != null ? request.getPhone().trim() : null);
            s.setAddress(request.getAddress() != null ? request.getAddress().trim() : null);
            s.setDateOfBirth(request.getDateOfBirth());
        } else if (entity instanceof Lecturer l) {
            l.setFullName(request.getFullName().trim());
            l.setPhone(request.getPhone() != null ? request.getPhone().trim() : null);
        } else if (entity instanceof Guardian g) {
            g.setFullName(request.getFullName().trim());
            g.setPhone(request.getPhone() != null ? request.getPhone().trim() : null);
        }
    }

    // --- Các hàm Mapping riêng biệt ---

    private ProfileResponse mapStudentToProfile(Account account, Student student) {
        return ProfileResponse.builder()
                .username(account.getUsername())
                .role(account.getRole().getRoleName())
                .fullName(student.getFullName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .nationalId(student.getNationalId())
                .address(student.getAddress())
                .dateOfBirth(student.getDateOfBirth())
                .studentCode(student.getStudentCode())
                .majorName(student.getMajor() != null ? student.getMajor().getMajorName() : null)
                .build();
    }

    private ProfileResponse mapLecturerToProfile(Account account, Lecturer lecturer) {
        return ProfileResponse.builder()
                .username(account.getUsername())
                .role(account.getRole().getRoleName())
                .fullName(lecturer.getFullName())
                .email(lecturer.getEmail())
                .phone(lecturer.getPhone())
                // Nếu muốn trả về lecturerCode hay academicDegree, bạn có thể thêm các field này vào DTO ProfileResponse
                // .lecturerCode(lecturer.getLecturerCode())
                .build();
    }

    private ProfileResponse mapGuardianToProfile(Account account, Guardian guardian) {
        return ProfileResponse.builder()
                .username(account.getUsername())
                .role(account.getRole().getRoleName())
                .fullName(guardian.getFullName())
                .phone(guardian.getPhone())
                // .relationship(guardian.getRelationship()) // Thêm vào DTO ProfileResponse nếu cần
                .build();
    }
}