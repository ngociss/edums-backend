package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.CourseStatus;
import com.G5C.EduMS.common.enums.GradeStatus;
import com.G5C.EduMS.common.enums.RegistrationStatus;
import com.G5C.EduMS.dto.request.CourseRegistrationRequest;
import com.G5C.EduMS.dto.request.CourseRegistrationSwitchRequest;
import com.G5C.EduMS.dto.response.CourseRegistrationResponse;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.CourseRegistrationMapper;
import com.G5C.EduMS.model.Account;
import com.G5C.EduMS.model.CourseRegistration;
import com.G5C.EduMS.model.CourseSection;
import com.G5C.EduMS.model.RecurringSchedule;
import com.G5C.EduMS.model.RegistrationPeriod;
import com.G5C.EduMS.model.Student;
import com.G5C.EduMS.repository.AccountRepository;
import com.G5C.EduMS.repository.CourseRegistrationRepository;
import com.G5C.EduMS.repository.CourseSectionRepository;
import com.G5C.EduMS.repository.GradeReportRepository;
import com.G5C.EduMS.repository.RecurringScheduleRepository;
import com.G5C.EduMS.repository.RegistrationPeriodRepository;
import com.G5C.EduMS.repository.StudentRepository;
import com.G5C.EduMS.service.CourseRegistrationService;
import com.G5C.EduMS.validator.CourseRegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseRegistrationServiceImpl implements CourseRegistrationService {

    private static final List<RegistrationStatus> ACTIVE_STATUSES =
            List.of(RegistrationStatus.PENDING, RegistrationStatus.CONFIRMED);

    private static final float MINIMUM_PASSING_SCORE = 4.0f;

    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseSectionRepository courseSectionRepository;
    private final RegistrationPeriodRepository registrationPeriodRepository;
    private final StudentRepository studentRepository;
    private final AccountRepository accountRepository;
    private final CourseRegistrationValidator courseRegistrationValidator;
    private final CourseRegistrationMapper courseRegistrationMapper;
    private final RecurringScheduleRepository recurringScheduleRepository;
    private final GradeReportRepository gradeReportRepository;

    @Value("${registration.max-credits-per-semester:25}")
    private int maxCreditsPerSemester;

    @Override
    @Transactional
    public CourseRegistrationResponse register(CourseRegistrationRequest request) {
        Student student = resolveStudentForWrite(request.getStudentId());
        CourseSection courseSection = getCourseSectionForUpdate(request.getCourseSectionId());

        CourseRegistration courseRegistration = prepareRegistration(student, courseSection, null);
        return courseRegistrationMapper.toResponse(courseRegistrationRepository.save(courseRegistration));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseRegistrationResponse> getCurrentStudentRegistrations(Integer semesterId) {
        Student student = getCurrentStudent();
        return getRegistrations(student.getId(), semesterId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseRegistrationResponse> getStudentRegistrations(Integer studentId, Integer semesterId) {
        studentRepository.findByIdAndDeletedFalse(studentId)
                .orElseThrow(() -> new NotFoundResourcesException("Student not found with id: " + studentId));

        return getRegistrations(studentId, semesterId);
    }

    @Override
    @Transactional
    public CourseRegistrationResponse cancel(Integer registrationId) {
        CourseRegistration registration = courseRegistrationRepository.findByIdAndDeletedFalseForUpdate(registrationId)
                .orElseThrow(() -> new NotFoundResourcesException("Course registration not found with id: " + registrationId));

        assertCanManageRegistration(registration);
        LocalDateTime now = LocalDateTime.now();
        courseRegistrationValidator.validateRegistrationActive(registration);
        courseRegistrationValidator.validateCanModifyDuringOpenPeriod(registration, now);

        registration.setStatus(RegistrationStatus.CANCELLED);
        return courseRegistrationMapper.toResponse(courseRegistrationRepository.save(registration));
    }

    @Override
    @Transactional
    public CourseRegistrationResponse switchSection(Integer registrationId, CourseRegistrationSwitchRequest request) {
        CourseRegistration currentRegistration = courseRegistrationRepository.findByIdAndDeletedFalseForUpdate(registrationId)
                .orElseThrow(() -> new NotFoundResourcesException("Course registration not found with id: " + registrationId));

        assertCanManageRegistration(currentRegistration);
        LocalDateTime now = LocalDateTime.now();
        courseRegistrationValidator.validateRegistrationActive(currentRegistration);
        courseRegistrationValidator.validateCanModifyDuringOpenPeriod(currentRegistration, now);

        CourseSection newSection = getCourseSectionForUpdate(request.getNewCourseSectionId());

        if (currentRegistration.getSection().getId().equals(newSection.getId())) {
            throw new InvalidDataException("Please choose a different course section to switch");
        }

        if (!currentRegistration.getSection().getSemester().getId().equals(newSection.getSemester().getId())) {
            throw new InvalidDataException("Only sections in the same semester can be switched");
        }

        if (!currentRegistration.getSection().getCourse().getId().equals(newSection.getCourse().getId())) {
            throw new InvalidDataException("Only sections of the same course can be switched");
        }

        CourseRegistration newRegistration = prepareRegistration(
                currentRegistration.getStudent(),
                newSection,
                currentRegistration.getId()
        );

        currentRegistration.setStatus(RegistrationStatus.CANCELLED);
        courseRegistrationRepository.save(currentRegistration);

        return courseRegistrationMapper.toResponse(courseRegistrationRepository.save(newRegistration));
    }

    private List<CourseRegistrationResponse> getRegistrations(Integer studentId, Integer semesterId) {
        return courseRegistrationRepository.findAllByStudentIdAndSemesterId(studentId, semesterId).stream()
                .sorted(Comparator.comparing(CourseRegistration::getRegistrationTime,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .map(courseRegistrationMapper::toResponse)
                .toList();
    }

    private CourseRegistration prepareRegistration(Student student, CourseSection courseSection, Integer excludedRegistrationId) {
        LocalDateTime now = LocalDateTime.now();

        courseRegistrationValidator.validateStudentActive(student);
        validateCourseSectionEligibility(courseSection);

        RegistrationPeriod registrationPeriod = registrationPeriodRepository
                .findOpenPeriodBySemesterId(courseSection.getSemester().getId(), now)
                .orElseThrow(() -> new InvalidDataException("Registration period is not open"));

        List<CourseRegistration> activeRegistrations = getActiveRegistrations(
                student.getId(),
                courseSection.getSemester().getId(),
                excludedRegistrationId
        );

        validateNoDuplicateSection(activeRegistrations, courseSection.getId());
        validateNoDuplicateCourse(activeRegistrations, courseSection.getCourse().getId());
        validateScheduleConflict(activeRegistrations, courseSection);
        validatePrerequisite(student, courseSection);
        validateCreditLimit(activeRegistrations, courseSection);
        courseRegistrationValidator.validateCapacity(courseSection);

        return CourseRegistration.builder()
                .student(student)
                .section(courseSection)
                .registrationPeriod(registrationPeriod)
                .registrationTime(now)
                .status(RegistrationStatus.CONFIRMED)
                .deleted(false)
                .build();
    }

    private List<CourseRegistration> getActiveRegistrations(Integer studentId, Integer semesterId, Integer excludedRegistrationId) {
        List<CourseRegistration> activeRegistrations = new ArrayList<>(
                courseRegistrationRepository.findActiveByStudentIdAndSemesterId(studentId, semesterId, ACTIVE_STATUSES)
        );

        if (excludedRegistrationId != null) {
            activeRegistrations.removeIf(registration -> registration.getId().equals(excludedRegistrationId));
        }

        return activeRegistrations;
    }

    private void validateCourseSectionEligibility(CourseSection courseSection) {
        courseRegistrationValidator.validateSectionOpen(courseSection);

        if (courseSection.getCourse() == null || courseSection.getCourse().getStatus() != CourseStatus.ACTIVE) {
            throw new InvalidDataException("Course is not active for registration");
        }

        if (courseSection.getMaxCapacity() == null || courseSection.getMaxCapacity() <= 0) {
            throw new InvalidDataException("Course section capacity is invalid");
        }

        if (courseSection.getCourse().getCredits() == null || courseSection.getCourse().getCredits() <= 0) {
            throw new InvalidDataException("Course credits are invalid");
        }
    }

    private void validateNoDuplicateSection(List<CourseRegistration> activeRegistrations, Integer courseSectionId) {
        boolean duplicatedSection = activeRegistrations.stream()
                .anyMatch(registration -> registration.getSection().getId().equals(courseSectionId));

        if (duplicatedSection) {
            throw new InvalidDataException("Student has already registered for this course section");
        }
    }

    private void validateNoDuplicateCourse(List<CourseRegistration> activeRegistrations, Integer courseId) {
        boolean duplicatedCourse = activeRegistrations.stream()
                .anyMatch(registration -> registration.getSection().getCourse().getId().equals(courseId));

        if (duplicatedCourse) {
            throw new InvalidDataException("Student has already registered for this course in another section");
        }
    }

    private void validateScheduleConflict(List<CourseRegistration> activeRegistrations, CourseSection newSection) {
        List<RecurringSchedule> newSchedules = recurringScheduleRepository
                .findAllBySectionIdAndDeletedFalse(newSection.getId());

        if (newSchedules.isEmpty()) {
            return;
        }

        for (CourseRegistration activeRegistration : activeRegistrations) {
            List<RecurringSchedule> existingSchedules = recurringScheduleRepository
                    .findAllBySectionIdAndDeletedFalse(activeRegistration.getSection().getId());

            for (RecurringSchedule existingSchedule : existingSchedules) {
                for (RecurringSchedule newSchedule : newSchedules) {
                    if (hasScheduleConflict(existingSchedule, newSchedule)) {
                        throw new InvalidDataException(
                                "Course section schedule conflicts with section " + activeRegistration.getSection().getSectionCode()
                        );
                    }
                }
            }
        }
    }

    private boolean hasScheduleConflict(RecurringSchedule first, RecurringSchedule second) {
        if (first.getDayOfWeek() == null || second.getDayOfWeek() == null) {
            return false;
        }

        boolean sameDay = first.getDayOfWeek().equals(second.getDayOfWeek());
        boolean overlapPeriod = first.getStartPeriod() <= second.getEndPeriod()
                && first.getEndPeriod() >= second.getStartPeriod();
        boolean overlapWeek = first.getStartWeek() <= second.getEndWeek()
                && first.getEndWeek() >= second.getStartWeek();

        return sameDay && overlapPeriod && overlapWeek;
    }

    private void validatePrerequisite(Student student, CourseSection courseSection) {
        if (courseSection.getCourse().getPrerequisiteCourse() == null) {
            return;
        }

        boolean passedPrerequisite = gradeReportRepository.existsPassedCourseByStudentIdAndCourseId(
                student.getId(),
                courseSection.getCourse().getPrerequisiteCourse().getId(),
                GradeStatus.PUBLISHED,
                MINIMUM_PASSING_SCORE
        );

        if (!passedPrerequisite) {
            throw new InvalidDataException("Student has not passed the prerequisite course");
        }
    }

    private void validateCreditLimit(List<CourseRegistration> activeRegistrations, CourseSection courseSection) {
        int currentCredits = activeRegistrations.stream()
                .map(CourseRegistration::getSection)
                .map(CourseSection::getCourse)
                .map(course -> course.getCredits() == null ? 0 : course.getCredits())
                .reduce(0, Integer::sum);

        int totalCredits = currentCredits + courseSection.getCourse().getCredits();
        if (totalCredits > maxCreditsPerSemester) {
            throw new InvalidDataException(
                    "Credit load exceeds the semester limit of " + maxCreditsPerSemester + " credits"
            );
        }
    }

    private Student resolveStudentForWrite(Integer requestedStudentId) {
        if (hasAnyRole("ADMIN", "MANAGER")) {
            if (requestedStudentId == null) {
                throw new InvalidDataException("studentId is required for admin or manager registrations");
            }

            return studentRepository.findByIdAndDeletedFalse(requestedStudentId)
                    .orElseThrow(() -> new NotFoundResourcesException("Student not found with id: " + requestedStudentId));
        }

        return getCurrentStudent();
    }

    private void assertCanManageRegistration(CourseRegistration registration) {
        if (hasAnyRole("ADMIN", "MANAGER")) {
            return;
        }

        Student currentStudent = getCurrentStudent();
        if (!registration.getStudent().getId().equals(currentStudent.getId())) {
            throw new InvalidDataException("You can only manage your own course registrations");
        }
    }

    private CourseSection getCourseSectionForUpdate(Integer courseSectionId) {
        return courseSectionRepository.findByIdAndDeletedFalseForUpdate(courseSectionId)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Course section not found with id: " + courseSectionId
                ));
    }

    private Student getCurrentStudent() {
        Account account = getAuthenticatedAccount();
        return studentRepository.findByAccount_IdAndDeletedFalse(account.getId())
                .orElseThrow(() -> new NotFoundResourcesException("Student not found for current account"));
    }

    private Account getAuthenticatedAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new InvalidDataException("Authentication information is missing");
        }

        return accountRepository.findByUsernameAndDeletedFalse(authentication.getName())
                .orElseThrow(() -> new NotFoundResourcesException("Account not found with username: " + authentication.getName()));
    }

    private boolean hasAnyRole(String... roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            for (String role : roles) {
                if (authority.getAuthority().equals("ROLE_" + role)) {
                    return true;
                }
            }
        }

        return false;
    }
}
