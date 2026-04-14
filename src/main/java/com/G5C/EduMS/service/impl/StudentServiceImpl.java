package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.StudentStatus;
import com.G5C.EduMS.dto.request.StudentCreateRequest;
import com.G5C.EduMS.dto.request.StudentStatusUpdateRequest;
import com.G5C.EduMS.dto.request.StudentUpdateRequest;
import com.G5C.EduMS.dto.response.StudentResponse;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.StudentMapper;
import com.G5C.EduMS.model.Account;
import com.G5C.EduMS.model.AdministrativeClass;
import com.G5C.EduMS.model.Guardian;
import com.G5C.EduMS.model.Major;
import com.G5C.EduMS.model.Specialization;
import com.G5C.EduMS.model.Student;
import com.G5C.EduMS.repository.AccountRepository;
import com.G5C.EduMS.repository.AdministrativeClassRepository;
import com.G5C.EduMS.repository.GuardianRepository;
import com.G5C.EduMS.repository.MajorRepository;
import com.G5C.EduMS.repository.SpecializationRepository;
import com.G5C.EduMS.repository.StudentRepository;
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
    private final AccountRepository accountRepository;

    private final AdministrativeClassRepository classRepository;
    private final MajorRepository majorRepository;
    private final SpecializationRepository specializationRepository;
    private final GuardianRepository guardianRepository;

    @Override
    public Page<StudentResponse> getAllStudents(int page, int size, String keyword, Integer classId, Integer majorId, StudentStatus status) {
        int safePage = page > 0 ? page - 1 : 0;
        Pageable pageable = PageRequest.of(safePage, size, Sort.by(Sort.Direction.DESC, "id"));
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
    public StudentResponse getCurrentStudent(String username) {
        Account account = accountRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy tài khoản với username: " + username));

        Student student = studentRepository.findByAccount_IdAndDeletedFalse(account.getId())
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy sinh viên tương ứng với tài khoản hiện tại"));

        return studentMapper.toResponse(student);
    }

    @Override
    @Transactional
    public StudentResponse createStudent(StudentCreateRequest request) {
        checkUniqueFieldsForCreate(request.getStudentCode(), request.getEmail(), request.getNationalId());

        Student student = studentMapper.toEntity(request);
        bindForeignKeys(student, request.getClassId(), request.getMajorId(), request.getSpecializationId(), request.getGuardianId());

        student = studentRepository.save(student);
        return studentMapper.toResponse(student);
    }

    @Override
    @Transactional
    public StudentResponse updateStudent(Integer id, StudentUpdateRequest request) {
        Student student = studentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy sinh viên với ID: " + id));

        checkUniqueFieldsForUpdate(student, request.getEmail(), request.getNationalId());
        studentMapper.updateStudentFromRequest(request, student);
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

    private void checkUniqueFieldsForCreate(String studentCode, String email, String nationalId) {
        if (studentCode == null || studentCode.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || nationalId == null || nationalId.trim().isEmpty()) {
            throw new InvalidDataException("Mã SV, Email và CCCD không được để trống.");
        }

        if (studentRepository.existsByStudentCodeAndDeletedFalse(studentCode)) {
            throw new ExistingResourcesException("Mã sinh viên đã tồn tại trong hệ thống.");
        }
        if (studentRepository.existsByEmailAndDeletedFalse(email)) {
            throw new ExistingResourcesException("Email đã được sử dụng.");
        }
        if (studentRepository.existsByNationalIdAndDeletedFalse(nationalId)) {
            throw new ExistingResourcesException("Số CCCD đã được sử dụng.");
        }
    }

    private void checkUniqueFieldsForUpdate(Student existingStudent, String newEmail, String newNationalId) {
        if (newEmail == null || newNationalId == null) {
            throw new InvalidDataException("Email và CCCD không được để trống.");
        }

        if (!existingStudent.getEmail().equals(newEmail) && studentRepository.existsByEmailAndDeletedFalse(newEmail)) {
            throw new ExistingResourcesException("Email mới đã được sử dụng bởi người khác.");
        }
        if (!existingStudent.getNationalId().equals(newNationalId)
                && studentRepository.existsByNationalIdAndDeletedFalse(newNationalId)) {
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

        if (specializationId != null) {
            Specialization spec = specializationRepository.findByIdAndDeletedFalse(specializationId)
                    .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Chuyên ngành với ID: " + specializationId));
            if (!spec.getMajor().getId().equals(majorId)) {
                throw new InvalidDataException("Chuyên ngành đã chọn không thuộc ngành học này");
            }
            student.setSpecialization(spec);
        } else {
            student.setSpecialization(null);
        }

        if (guardianId != null) {
            Guardian guardian = guardianRepository.findByIdAndDeletedFalse(guardianId)
                    .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Phụ huynh với ID: " + guardianId));
            student.setGuardian(guardian);
        } else {
            student.setGuardian(null);
        }
    }
}
