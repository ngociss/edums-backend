package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.RegistrationStatus;
import com.G5C.EduMS.dto.request.CourseRegistrationRequest;
import com.G5C.EduMS.dto.response.CourseRegistrationResponse;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.model.Account;
import com.G5C.EduMS.model.CourseRegistration;
import com.G5C.EduMS.model.CourseSection;
import com.G5C.EduMS.model.RegistrationPeriod;
import com.G5C.EduMS.model.Student;
import com.G5C.EduMS.repository.AccountRepository;
import com.G5C.EduMS.repository.CourseRegistrationRepository;
import com.G5C.EduMS.repository.CourseSectionRepository;
import com.G5C.EduMS.repository.RegistrationPeriodRepository;
import com.G5C.EduMS.repository.StudentRepository;
import com.G5C.EduMS.service.CourseRegistrationService;
import com.G5C.EduMS.mapper.CourseRegistrationMapper;
import com.G5C.EduMS.validator.CourseRegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CourseRegistrationServiceImpl implements CourseRegistrationService {

    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseSectionRepository courseSectionRepository;
    private final RegistrationPeriodRepository registrationPeriodRepository;
    private final StudentRepository studentRepository;
    private final AccountRepository accountRepository;
    private final CourseRegistrationValidator courseRegistrationValidator;
    private final CourseRegistrationMapper courseRegistrationMapper;

    @Override
    @Transactional
    public CourseRegistrationResponse register(CourseRegistrationRequest request) {
        Student student = getCurrentStudent();

        CourseSection courseSection = courseSectionRepository
                .findByIdAndDeletedFalse(request.getCourseSectionId())
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Course section not found with id: " + request.getCourseSectionId()
                ));

        courseRegistrationValidator.validateStudentActive(student);
        courseRegistrationValidator.validateSectionOpen(courseSection);

        RegistrationPeriod registrationPeriod = registrationPeriodRepository
                .findOpenPeriodBySemesterId(courseSection.getSemester().getId(), LocalDateTime.now())
                .orElseThrow(() -> new InvalidDataException("Registration period is not open"));

        courseRegistrationValidator.validateNotDuplicate(student.getId(), courseSection.getId());
        courseRegistrationValidator.validateCapacity(courseSection);

        CourseRegistration courseRegistration = CourseRegistration.builder()
                .student(student)
                .section(courseSection)
                .registrationPeriod(registrationPeriod)
                .registrationTime(LocalDateTime.now())
                .status(RegistrationStatus.CONFIRMED)
                .deleted(false)
                .build();

        return courseRegistrationMapper.toResponse(
                courseRegistrationRepository.save(courseRegistration)
        );
    }

    private Student getCurrentStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Account account = accountRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new NotFoundResourcesException("Account not found with username: " + username));

        return studentRepository.findByIdAndDeletedFalse(account.getId())
                .orElseThrow(() -> new NotFoundResourcesException("Student not found for current account"));
    }
}