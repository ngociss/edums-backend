package com.G5C.EduMS.validator;


import com.G5C.EduMS.common.enums.CourseSectionStatus;
import com.G5C.EduMS.common.enums.RegistrationStatus;
import com.G5C.EduMS.common.enums.StudentStatus;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.model.CourseRegistration;
import com.G5C.EduMS.model.CourseSection;
import com.G5C.EduMS.model.Student;
import com.G5C.EduMS.repository.CourseRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseRegistrationValidator {

    private final CourseRegistrationRepository courseRegistrationRepository;

    public void validateStudentActive(Student student) {
        if (student.getStatus() != StudentStatus.ACTIVE) {
            throw new InvalidDataException("Sinh viên không ở trạng thái đang học");
        }
    }

    public void validateSectionOpen(CourseSection courseSection) {
        if (courseSection.getStatus() != CourseSectionStatus.OPEN) {
            throw new InvalidDataException("Lớp học phần chưa mở đăng ký");
        }
    }

    public void validateNotDuplicate(Integer studentId, Integer courseSectionId) {
        boolean exists = courseRegistrationRepository
                .existsByStudent_IdAndSection_IdAndStatusInAndDeletedFalse(studentId, courseSectionId, List.of(RegistrationStatus.PENDING, RegistrationStatus.CONFIRMED));

        if (exists) {
            throw new ExistingResourcesException("Sinh viên đã đăng ký lớp học phần này");
        }
    }

    public void validateCapacity(CourseSection courseSection) {
        long registeredCount = courseRegistrationRepository
                .countBySection_IdAndStatusAndDeletedFalse(
                        courseSection.getId(),
                        RegistrationStatus.CONFIRMED
                );

        if (registeredCount >= courseSection.getMaxCapacity()) {
            throw new InvalidDataException("Lớp học phần đã đủ sĩ số");
        }
    }

    public void validateRegistrationActive(CourseRegistration registration) {
        if (!List.of(RegistrationStatus.PENDING, RegistrationStatus.CONFIRMED).contains(registration.getStatus())) {
            throw new InvalidDataException("Chỉ có thể thao tác với đăng ký còn hiệu lực");
        }
    }

    public void validateCanModifyDuringOpenPeriod(CourseRegistration registration, LocalDateTime now) {
        if (registration.getRegistrationPeriod() == null
                || registration.getRegistrationPeriod().getStatus() != com.G5C.EduMS.common.enums.RegistrationPeriodStatus.OPEN
                || registration.getRegistrationPeriod().getStartTime().isAfter(now)
                || registration.getRegistrationPeriod().getEndTime().isBefore(now)) {
            throw new InvalidDataException("Chỉ có thể thay đổi đăng ký trong thời gian đợt đăng ký đang mở");
        }
    }
}
