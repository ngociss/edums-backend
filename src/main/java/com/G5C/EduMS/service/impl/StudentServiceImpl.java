package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.StudentStatus;
import com.G5C.EduMS.dto.response.StudentResponse;
import com.G5C.EduMS.dto.request.StudentCreateRequest;
import com.G5C.EduMS.dto.request.StudentStatusUpdateRequest;
import com.G5C.EduMS.dto.request.StudentUpdateRequest;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.InvalidDataException;
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
import org.springframework.data.domain.Sort;
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
        int safePage = page > 0 ? page - 1 : 0;
        Pageable pageable = PageRequest.of(safePage, size, Sort.by(Sort.Direction.DESC, "id"));
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
        if (request.getStatus() == null) {
            throw new InvalidDataException("Trạng thái không được để trống.");
        }
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
        if (studentCode == null || studentCode.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                nationalId == null || nationalId.trim().isEmpty()) {
            throw new InvalidDataException("Mã SV, Email và CCCD không được để trống.");
        }
        if (studentRepository.existsByStudentCodeAndDeletedFalse(studentCode))
            throw new ExistingResourcesException("Mã sinh viên đã tồn tại trong hệ thống.");
        if (studentRepository.existsByEmailAndDeletedFalse(email))
            throw new ExistingResourcesException("Email đã được sử dụng.");
        if (studentRepository.existsByNationalIdAndDeletedFalse(nationalId))
            throw new ExistingResourcesException("Số CCCD đã được sử dụng.");
    }

    private void checkUniqueFieldsForUpdate(Student existingStudent, String newEmail, String newNationalId) {
        if (newEmail == null || newNationalId == null) {
            throw new InvalidDataException("Email và CCCD không được để trống.");
        }
        if (!existingStudent.getEmail().equals(newEmail) && studentRepository.existsByEmailAndDeletedFalse(newEmail)) {
            throw new ExistingResourcesException("Email mới đã được sử dụng bởi người khác.");
        }
        if (!existingStudent.getNationalId().equals(newNationalId) && studentRepository.existsByNationalIdAndDeletedFalse(newNationalId)) {
            throw new ExistingResourcesException("Số CCCD mới đã được sử dụng bởi người khác.");
        }
    }

    private void bindForeignKeys(Student student, Integer classId, Integer majorId, Integer specializationId, Integer guardianId) {
        if (classId == null || majorId == null) {
            throw new InvalidDataException("Sinh viên bắt buộc phải thuộc một Lớp hành chính và một Ngành học.");
        }

        AdministrativeClass adminClass = classRepository.findByIdAndDeletedFalse(classId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Lớp với ID: " + classId));
        student.setAdministrativeClass(adminClass);

        Major major = majorRepository.findByIdAndDeletedFalse(majorId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Ngành với ID: " + majorId));
        student.setMajor(major);

        // Ràng buộc Chuyên ngành (Tùy chọn)
        if (specializationId != null) {
            Specialization spec = specializationRepository.findByIdAndDeletedFalse(specializationId)
                    .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Chuyên ngành với ID: " + specializationId));
            if(!spec.getMajor().getId().equals(majorId)){
                throw new InvalidDataException("Chuyên ngành đã chọn không thuộc ngành học này");
            }
            student.setSpecialization(spec);
        } else {
            student.setSpecialization(null);
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
