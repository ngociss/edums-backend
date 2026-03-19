package com.G5C.EduMS.validator;


import com.G5C.EduMS.common.enums.CourseSectionStatus;
import com.G5C.EduMS.common.enums.RegistrationStatus;
import com.G5C.EduMS.common.enums.StudentStatus;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.model.CourseSection;
import com.G5C.EduMS.model.Student;
import com.G5C.EduMS.repository.CourseRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseRegistrationValidator {

    private final CourseRegistrationRepository courseRegistrationRepository;

    public void validateStudentActive(Student student) {
        if (student.getStatus() != StudentStatus.ACTIVE) {
            throw new InvalidDataException("Student is not active");
        }
    }

    public void validateSectionOpen(CourseSection courseSection) {
        if (courseSection.getStatus() != CourseSectionStatus.OPEN) {
            throw new InvalidDataException("Course section is not open for registration");
        }
    }

    public void validateNotDuplicate(Integer studentId, Integer courseSectionId) {
        boolean exists = courseRegistrationRepository
                .existsByStudent_IdAndSection_IdAndStatusInAndDeletedFalse(studentId, courseSectionId, List.of(RegistrationStatus.PENDING, RegistrationStatus.CONFIRMED));

        if (exists) {
            throw new ExistingResourcesException("Student has already registered for this course section");
        }
    }

    public void validateCapacity(CourseSection courseSection) {
        long registeredCount = courseRegistrationRepository
                .countBySection_IdAndStatusAndDeletedFalse(
                        courseSection.getId(),
                        RegistrationStatus.CONFIRMED
                );

        if (registeredCount >= courseSection.getMaxCapacity()) {
            throw new InvalidDataException("Course section is full");
        }
    }
}