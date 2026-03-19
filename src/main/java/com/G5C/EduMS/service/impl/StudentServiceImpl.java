package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.StudentStatus;
import com.G5C.EduMS.dto.response.StudentResponse;
import com.G5C.EduMS.dto.request.StudentCreateRequest;
import com.G5C.EduMS.dto.request.StudentStatusUpdateRequest;
import com.G5C.EduMS.dto.request.StudentUpdateRequest;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.StudentMapper;
import com.G5C.EduMS.model.*;
import com.G5C.EduMS.repository.*;
import com.G5C.EduMS.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    // Inject các Repository của khóa ngoại
    private final AdministrativeClassRepository classRepository;
    private final MajorRepository majorRepository;
    private final SpecializationRepository specializationRepository;
    private final GuardianRepository guardianRepository;

    @Override
    public Page<StudentResponse> getAllStudents(int page, int size, String keyword, Integer classId, Integer majorId, StudentStatus status) {
        Pageable pageable = PageRequest.of(page, size);

        // Gọi hàm custom query trong Repository để lọc đa chiều
        Page<Student> studentPage = studentRepository.searchStudents(keyword, classId, majorId, status, pageable);

        return studentPage.map(studentMapper::toResponse);
    }

    @Override
    public StudentResponse getStudentById(Integer id) {
        Student student = studentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy sinh viên với ID: " + id));
        return studentMapper.toResponse(student);
    }

    @Override
    @Transactional
    public StudentResponse createStudent(StudentCreateRequest request) {
        // 1. Kiểm tra các trường Unique
        checkUniqueFieldsForCreate(request.getStudentCode(), request.getEmail(), request.getNationalId());

        // 2. Map dữ liệu cơ bản từ DTO sang Entity
        Student student = studentMapper.toEntity(request);

        // 3. Xử lý gắn các khóa ngoại (Foreign Keys)
        bindForeignKeys(student, request.getClassId(), request.getMajorId(), request.getSpecializationId(), request.getGuardianId());

        // 4. Lưu vào Database
        student = studentRepository.save(student);
        return studentMapper.toResponse(student);
    }

    @Override
    @Transactional
    public StudentResponse updateStudent(Integer id, StudentUpdateRequest request) {
        // 1. Tìm sinh viên hiện tại
        Student student = studentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy sinh viên với ID: " + id));

        // 2. Kiểm tra trùng lặp nếu có thay đổi Email hoặc CCCD
        checkUniqueFieldsForUpdate(student, request.getEmail(), request.getNationalId());

        // 3. Map dữ liệu cơ bản đè lên Entity cũ
        studentMapper.updateStudentFromRequest(request, student);

        // 4. Cập nhật lại các khóa ngoại nếu có thay đổi
        bindForeignKeys(student, request.getClassId(), request.getMajorId(), request.getSpecializationId(), request.getGuardianId());

        student = studentRepository.save(student);
        return studentMapper.toResponse(student);
    }

    @Override
    @Transactional
    public void updateStudentStatus(Integer id, StudentStatusUpdateRequest request) {
        Student student = studentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy sinh viên với ID: " + id));

        student.setStatus(request.getStatus());
        studentRepository.save(student);
        log.info("Đã cập nhật trạng thái sinh viên ID {} thành {}", id, request.getStatus());
    }

    @Override
    @Transactional
    public void deleteStudent(Integer id) {
        Student student = studentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy sinh viên với ID: " + id));

        student.setDeleted(true);
        studentRepository.save(student);
        log.info("Đã xóa mềm sinh viên có ID: {}", id);
    }

    // ================== CÁC HÀM PRIVATE HỖ TRỢ LOGIC ==================

    private void checkUniqueFieldsForCreate(String studentCode, String email, String nationalId) {
        if (studentRepository.existsByStudentCodeAndDeletedFalse(studentCode))
            throw new IllegalArgumentException("Mã sinh viên đã tồn tại trong hệ thống.");
        if (studentRepository.existsByEmailAndDeletedFalse(email))
            throw new IllegalArgumentException("Email đã được sử dụng.");
        if (studentRepository.existsByNationalIdAndDeletedFalse(nationalId))
            throw new IllegalArgumentException("Số CCCD đã được sử dụng.");
    }

    private void checkUniqueFieldsForUpdate(Student existingStudent, String newEmail, String newNationalId) {
        if (!existingStudent.getEmail().equals(newEmail) && studentRepository.existsByEmailAndDeletedFalse(newEmail)) {
            throw new IllegalArgumentException("Email mới đã được sử dụng bởi người khác.");
        }
        if (!existingStudent.getNationalId().equals(newNationalId) && studentRepository.existsByNationalIdAndDeletedFalse(newNationalId)) {
            throw new IllegalArgumentException("Số CCCD mới đã được sử dụng bởi người khác.");
        }
    }

    private void bindForeignKeys(Student student, Integer classId, Integer majorId, Integer specializationId, Integer guardianId) {
        // Ràng buộc Lớp và Ngành (Bắt buộc)
        AdministrativeClass adminClass = classRepository.findById(classId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Lớp với ID: " + classId));
        student.setAdministrativeClass(adminClass);

        Major major = majorRepository.findById(majorId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Ngành với ID: " + majorId));
        student.setMajor(major);

        // Ràng buộc Chuyên ngành (Tùy chọn)
        if (specializationId != null) {
            Specialization spec = specializationRepository.findById(specializationId)
                    .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Chuyên ngành với ID: " + specializationId));
            student.setSpecialization(spec);
        } else {
            student.setSpecialization(null); // Xóa liên kết nếu frontend gửi lên null
        }

        // Ràng buộc Phụ huynh (Tùy chọn)
        if (guardianId != null) {
            Guardian guardian = guardianRepository.findByIdAndDeletedFalse(guardianId)
                    .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Phụ huynh với ID: " + guardianId));
            student.setGuardian(guardian);
        } else {
            student.setGuardian(null);
        }
    }
}
