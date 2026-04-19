package com.G5C.EduMS.validator;

import com.G5C.EduMS.dto.request.AdministrativeClassRequest;
import com.G5C.EduMS.exception.CannotDeleteException;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.repository.AdministrativeClassRepository;
import com.G5C.EduMS.repository.CohortRepository;
import com.G5C.EduMS.repository.LecturerRepository;
import com.G5C.EduMS.repository.MajorRepository;
import com.G5C.EduMS.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdministrativeClassValidator {

    private final AdministrativeClassRepository administrativeClassRepository;
    private final LecturerRepository lecturerRepository;
    private final CohortRepository cohortRepository;
    private final MajorRepository majorRepository;
    private final StudentRepository studentRepository;

    public void validateCreate(AdministrativeClassRequest request) {
        if (administrativeClassRepository.existsByClassNameAndDeletedFalse(request.getClassName()))
            throw new ExistingResourcesException(
                "Lớp hành chính đã tồn tại với tên: " + request.getClassName());

        if (!lecturerRepository.existsById(request.getHeadLecturerId()))
            throw new NotFoundResourcesException(
                "Không tìm thấy giảng viên với id: " + request.getHeadLecturerId());

        if (!cohortRepository.existsById(request.getCohortId()))
            throw new NotFoundResourcesException(
                "Không tìm thấy khóa học với id: " + request.getCohortId());

        if (!majorRepository.existsById(request.getMajorId()))
            throw new NotFoundResourcesException(
                "Không tìm thấy ngành học với id: " + request.getMajorId());
    }

    public void validateUpdate(Integer id, AdministrativeClassRequest request) {
        if (administrativeClassRepository.existsByClassNameAndDeletedFalseAndIdNot(
                request.getClassName(), id))
            throw new ExistingResourcesException(
                "Lớp hành chính đã tồn tại với tên: " + request.getClassName());

        if (!lecturerRepository.existsById(request.getHeadLecturerId()))
            throw new NotFoundResourcesException(
                "Không tìm thấy giảng viên với id: " + request.getHeadLecturerId());

        if (!cohortRepository.existsById(request.getCohortId()))
            throw new NotFoundResourcesException(
                "Không tìm thấy khóa học với id: " + request.getCohortId());

        if (!majorRepository.existsById(request.getMajorId()))
            throw new NotFoundResourcesException(
                "Không tìm thấy ngành học với id: " + request.getMajorId());
    }

    public void validateDelete(Integer id) {
        if (studentRepository.existsByAdministrativeClassIdAndDeletedFalse(id))
            throw new CannotDeleteException(
                "Không thể xóa: lớp hành chính vẫn còn sinh viên đang học");
    }
}
