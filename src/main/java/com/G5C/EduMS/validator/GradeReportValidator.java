package com.G5C.EduMS.validator;

import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
                "Course registration not found with id: " + registrationId);

        if (gradeReportRepository.existsByRegistrationIdAndDeletedFalse(registrationId))
            throw new ExistingResourcesException(
                "Grade report already exists for registration: " + registrationId);
    }

    public void validateUpdate(Integer id) {
        if (gradeReportRepository.findByIdAndDeletedFalse(id).isEmpty())
            throw new NotFoundResourcesException("Grade report not found with id: " + id);
    }

    public void validateComponent(Integer componentId) {
        if (gradeComponentRepository.findByIdAndDeletedFalse(componentId).isEmpty())
            throw new NotFoundResourcesException(
                "Grade component not found with id: " + componentId);
    }

    public void validateDuplicateDetail(Integer reportId, Integer componentId) {
        if (gradeDetailRepository.existsByReportIdAndComponentIdAndDeletedFalse(reportId, componentId))
            throw new ExistingResourcesException(
                "Grade detail already exists for component: " + componentId);
    }
}
