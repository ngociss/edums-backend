package com.G5C.EduMS.validator;

import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class GradeReportValidator {

    private final GradeReportRepository gradeReportRepository;
    private final GradeDetailRepository gradeDetailRepository;
    private final GradeComponentRepository gradeComponentRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;

    public void validateCreate(Integer registrationId) {
        if (!courseRegistrationRepository.existsById(registrationId))
            throw new NotFoundResourcesException(
                "Không tìm thấy đăng ký học phần với id: " + registrationId);

        if (gradeReportRepository.existsByRegistrationIdAndDeletedFalse(registrationId))
            throw new ExistingResourcesException(
                "Phiếu điểm đã tồn tại cho đăng ký học phần: " + registrationId);
    }

    public void validateUpdate(Integer id) {
        if (gradeReportRepository.findByIdAndDeletedFalse(id).isEmpty())
            throw new NotFoundResourcesException("Không tìm thấy phiếu điểm với id: " + id);
    }

    public void validateComponent(Integer componentId) {
        if (gradeComponentRepository.findByIdAndDeletedFalse(componentId).isEmpty())
            throw new NotFoundResourcesException(
                "Không tìm thấy thành phần điểm với id: " + componentId);
    }

    public void validateDuplicateDetail(Integer reportId, Integer componentId) {
        if (gradeDetailRepository.existsByReportIdAndComponentIdAndDeletedFalse(reportId, componentId))
            throw new ExistingResourcesException(
                "Chi tiết điểm đã tồn tại cho thành phần điểm: " + componentId);
    }

    public void validateRequestDetails(List<Integer> componentIds) {
        Set<Integer> seen = new HashSet<>();
        for (Integer componentId : componentIds) {
            if (!seen.add(componentId)) {
                throw new ExistingResourcesException(
                        "Dữ liệu chi tiết điểm chứa thành phần điểm bị trùng: " + componentId);
            }
        }
    }
}
