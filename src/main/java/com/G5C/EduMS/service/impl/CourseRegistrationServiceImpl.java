package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.CourseStatus;
import com.G5C.EduMS.common.enums.GradeStatus;
import com.G5C.EduMS.common.enums.RegistrationStatus;
import com.G5C.EduMS.dto.request.CourseRegistrationRequest;
import com.G5C.EduMS.dto.request.CourseRegistrationSwitchRequest;
import com.G5C.EduMS.dto.response.AvailableCourseSectionResponse;
import com.G5C.EduMS.dto.response.AvailableCourseSectionScheduleResponse;
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

import java.time.LocalDate;
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
    @Transactional(readOnly = true)
    public List<AvailableCourseSectionResponse> getAvailableSections(
            Integer facultyId,
            Integer courseId,
            Integer semesterId,
            String keyword
    ) {
        LocalDateTime now = LocalDateTime.now();
        List<RegistrationPeriod> openPeriods = registrationPeriodRepository.findAllOpenPeriods(now);

        if (openPeriods.isEmpty()) {
            return List.of();
        }

        var openPeriodBySemesterId = openPeriods.stream()
                .collect(java.util.stream.Collectors.toMap(
                        period -> period.getSemester().getId(),
                        period -> period,
                        (first, second) -> first
                ));

        return courseSectionRepository.findAvailableForRegistration(now, facultyId, courseId, semesterId, keyword)
                .stream()
                .map(section -> toAvailableSectionResponse(section, openPeriodBySemesterId.get(section.getSemester().getId())))
                .toList();
    }

    @Override
    @Transactional
    public CourseRegistrationResponse register(CourseRegistrationRequest request) {
        Student student = getCurrentStudentForUpdate();
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
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy sinh viên với id: " + studentId));

        return getRegistrations(studentId, semesterId);
    }

    @Override
    @Transactional
    public CourseRegistrationResponse cancel(Integer registrationId) {
        CourseRegistration registrationSnapshot = courseRegistrationRepository.findByIdAndDeletedFalse(registrationId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy đăng ký học phần với id: " + registrationId));

        assertCanManageRegistration(registrationSnapshot);
        lockStudentForWrite(registrationSnapshot.getStudent().getId());

        CourseRegistration registration = courseRegistrationRepository.findByIdAndDeletedFalseForUpdate(registrationId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy đăng ký học phần với id: " + registrationId));

        assertCanManageRegistration(registration);
        LocalDateTime now = LocalDateTime.now();
        courseRegistrationValidator.validateRegistrationActive(registration);
        courseRegistrationValidator.validateCanModifyDuringOpenPeriod(registration, now);

        CourseRegistrationResponse response = courseRegistrationMapper.toResponse(registration);
        courseRegistrationRepository.delete(registration);
        return response;
    }

    @Override
    @Transactional
    public CourseRegistrationResponse switchSection(Integer registrationId, CourseRegistrationSwitchRequest request) {
        CourseRegistration registrationSnapshot = courseRegistrationRepository.findByIdAndDeletedFalse(registrationId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy đăng ký học phần với id: " + registrationId));

        assertCanManageRegistration(registrationSnapshot);
        lockStudentForWrite(registrationSnapshot.getStudent().getId());

        CourseRegistration currentRegistration = courseRegistrationRepository.findByIdAndDeletedFalseForUpdate(registrationId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy đăng ký học phần với id: " + registrationId));

        assertCanManageRegistration(currentRegistration);
        LocalDateTime now = LocalDateTime.now();
        courseRegistrationValidator.validateRegistrationActive(currentRegistration);
        courseRegistrationValidator.validateCanModifyDuringOpenPeriod(currentRegistration, now);

        CourseSection newSection = getCourseSectionForUpdate(request.getNewCourseSectionId());

        if (currentRegistration.getSection().getId().equals(newSection.getId())) {
            throw new InvalidDataException("Vui lòng chọn lớp học phần khác để chuyển");
        }

        if (!currentRegistration.getSection().getSemester().getId().equals(newSection.getSemester().getId())) {
            throw new InvalidDataException("Chỉ được chuyển sang lớp học phần trong cùng học kỳ");
        }

        if (!currentRegistration.getSection().getCourse().getId().equals(newSection.getCourse().getId())) {
            throw new InvalidDataException("Chỉ được chuyển sang lớp học phần của cùng một môn học");
        }

        CourseRegistration newRegistration = prepareRegistration(
                currentRegistration.getStudent(),
                newSection,
                currentRegistration.getId()
        );

        CourseRegistration savedRegistration = courseRegistrationRepository.save(newRegistration);
        courseRegistrationRepository.delete(currentRegistration);
        return courseRegistrationMapper.toResponse(savedRegistration);
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
                .orElseThrow(() -> new InvalidDataException("Đợt đăng ký học phần hiện không mở"));

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
            throw new InvalidDataException("Môn học hiện không được phép đăng ký");
        }

        if (courseSection.getMaxCapacity() == null || courseSection.getMaxCapacity() <= 0) {
            throw new InvalidDataException("Sĩ số tối đa của lớp học phần không hợp lệ");
        }

        if (courseSection.getCourse().getCredits() == null || courseSection.getCourse().getCredits() <= 0) {
            throw new InvalidDataException("Số tín chỉ của môn học không hợp lệ");
        }
    }

    private void validateNoDuplicateSection(List<CourseRegistration> activeRegistrations, Integer courseSectionId) {
        boolean duplicatedSection = activeRegistrations.stream()
                .anyMatch(registration -> registration.getSection().getId().equals(courseSectionId));

        if (duplicatedSection) {
            throw new InvalidDataException("Sinh viên đã đăng ký lớp học phần này");
        }
    }

    private void validateNoDuplicateCourse(List<CourseRegistration> activeRegistrations, Integer courseId) {
        boolean duplicatedCourse = activeRegistrations.stream()
                .anyMatch(registration -> registration.getSection().getCourse().getId().equals(courseId));

        if (duplicatedCourse) {
            throw new InvalidDataException("Sinh viên đã đăng ký môn học này ở lớp học phần khác");
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
                                "Lịch học của lớp học phần bị trùng với lớp " + activeRegistration.getSection().getDisplayName()
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

    private AvailableCourseSectionResponse toAvailableSectionResponse(CourseSection section, RegistrationPeriod registrationPeriod) {
        long registeredCount = courseRegistrationRepository.countBySection_IdAndStatusAndDeletedFalse(
                section.getId(),
                RegistrationStatus.CONFIRMED
        );

        long remainingCapacity = section.getMaxCapacity() == null
                ? 0
                : Math.max(section.getMaxCapacity() - registeredCount, 0);

        List<AvailableCourseSectionScheduleResponse> schedules = recurringScheduleRepository
                .findAllBySectionIdAndDeletedFalse(section.getId())
                .stream()
                .map(schedule -> {
                    int startWeek = resolveScheduleStartWeek(schedule);
                    int endWeek = resolveScheduleEndWeek(schedule);

                    return AvailableCourseSectionScheduleResponse.builder()
                        .roomId(schedule.getRoom() == null ? null : schedule.getRoom().getId())
                        .roomName(schedule.getRoom() == null ? null : schedule.getRoom().getRoomName())
                        .dayOfWeek(schedule.getDayOfWeek())
                        .startPeriod(schedule.getStartPeriod())
                        .endPeriod(schedule.getEndPeriod())
                        .startWeek(startWeek)
                        .endWeek(endWeek)
                        .effectiveStartDate(resolveEffectiveDate(schedule, startWeek))
                        .effectiveEndDate(resolveEffectiveDate(schedule, endWeek))
                        .build();
                })
                .toList();

        return AvailableCourseSectionResponse.builder()
                .courseSectionId(section.getId())
                .sectionCode(section.getSectionCode())
                .displayName(section.getDisplayName())
                .courseId(section.getCourse().getId())
                .courseCode(section.getCourse().getCourseCode())
                .courseName(section.getCourse().getCourseName())
                .credits(section.getCourse().getCredits())
                .facultyId(section.getCourse().getFaculty() == null ? null : section.getCourse().getFaculty().getId())
                .facultyName(section.getCourse().getFaculty() == null ? null : section.getCourse().getFaculty().getFacultyName())
                .prerequisiteCourseId(section.getCourse().getPrerequisiteCourse() == null ? null : section.getCourse().getPrerequisiteCourse().getId())
                .prerequisiteCourseCode(section.getCourse().getPrerequisiteCourse() == null ? null : section.getCourse().getPrerequisiteCourse().getCourseCode())
                .prerequisiteCourseName(section.getCourse().getPrerequisiteCourse() == null ? null : section.getCourse().getPrerequisiteCourse().getCourseName())
                .lecturerId(section.getLecturer() == null ? null : section.getLecturer().getId())
                .lecturerName(section.getLecturer() == null ? null : section.getLecturer().getFullName())
                .semesterId(section.getSemester().getId())
                .semesterNumber(section.getSemester().getSemesterNumber())
                .academicYear(section.getSemester().getAcademicYear())
                .registrationPeriodId(registrationPeriod == null ? null : registrationPeriod.getId())
                .registrationPeriodName(registrationPeriod == null ? null : registrationPeriod.getName())
                .registrationStartTime(registrationPeriod == null ? null : registrationPeriod.getStartTime())
                .registrationEndTime(registrationPeriod == null ? null : registrationPeriod.getEndTime())
                .maxCapacity(section.getMaxCapacity())
                .registeredCount(registeredCount)
                .remainingCapacity(remainingCapacity)
                .status(section.getStatus())
                .schedules(schedules)
                .build();
    }

    private LocalDate resolveEffectiveDate(RecurringSchedule schedule, Integer weekNumber) {
        if (schedule == null
                || weekNumber == null
                || schedule.getDayOfWeek() == null
                || schedule.getSection() == null
                || schedule.getSection().getSemester() == null
                || schedule.getSection().getSemester().getStartDate() == null) {
            return null;
        }

        LocalDate semesterStart = schedule.getSection().getSemester().getStartDate();
        LocalDate weekStart = semesterStart.plusWeeks(Math.max(weekNumber - 1L, 0L));
        java.time.DayOfWeek targetDay = com.G5C.EduMS.common.enums.DayOfWeek
                .fromValue(schedule.getDayOfWeek())
                .toJavaDayOfWeek();

        long daysOffset = targetDay.getValue() - weekStart.getDayOfWeek().getValue();
        if (daysOffset < 0) {
            daysOffset += 7;
        }

        return weekStart.plusDays(daysOffset);
    }

    private int resolveScheduleStartWeek(RecurringSchedule schedule) {
        return schedule.getStartWeek() == null ? 1 : schedule.getStartWeek();
    }

    private int resolveScheduleEndWeek(RecurringSchedule schedule) {
        if (schedule.getEndWeek() != null) {
            return schedule.getEndWeek();
        }
        if (schedule.getSection() != null
                && schedule.getSection().getSemester() != null
                && schedule.getSection().getSemester().getTotalWeeks() != null) {
            return schedule.getSection().getSemester().getTotalWeeks();
        }
        return resolveScheduleStartWeek(schedule);
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
            throw new InvalidDataException("Sinh viên chưa đạt môn học tiên quyết: " + courseSection.getCourse().getPrerequisiteCourse().getCourseName());
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
                    "Tổng số tín chỉ vượt quá giới hạn " + maxCreditsPerSemester + " tín chỉ trong học kỳ"
            );
        }
    }

    private Student resolveStudentForWrite(Integer requestedStudentId) {
        if (hasAnyRole("ADMIN", "MANAGER")) {
            if (requestedStudentId == null) {
                throw new InvalidDataException("Admin hoặc quản lý phải truyền studentId khi đăng ký hộ");
            }

            return studentRepository.findByIdAndDeletedFalseForUpdate(requestedStudentId)
                    .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy sinh viên với id: " + requestedStudentId));
        }

        return getCurrentStudentForUpdate();
    }

    private void assertCanManageRegistration(CourseRegistration registration) {
        if (hasAnyRole("ADMIN", "MANAGER")) {
            return;
        }

        Student currentStudent = getCurrentStudent();
        if (!registration.getStudent().getId().equals(currentStudent.getId())) {
            throw new InvalidDataException("Bạn chỉ có thể thao tác trên đăng ký học phần của chính mình");
        }
    }

    private CourseSection getCourseSectionForUpdate(Integer courseSectionId) {
        return courseSectionRepository.findByIdAndDeletedFalseForUpdate(courseSectionId)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy lớp học phần với id: " + courseSectionId
                ));
    }

    private Student getCurrentStudent() {
        Account account = getAuthenticatedAccount();
        return studentRepository.findByAccount_IdAndDeletedFalse(account.getId())
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy sinh viên tương ứng với tài khoản hiện tại"));
    }

    private Student getCurrentStudentForUpdate() {
        Account account = getAuthenticatedAccount();
        return studentRepository.findByAccountIdAndDeletedFalseForUpdate(account.getId())
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy sinh viên tương ứng với tài khoản hiện tại"));
    }

    private Student lockStudentForWrite(Integer studentId) {
        return studentRepository.findByIdAndDeletedFalseForUpdate(studentId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy sinh viên với id: " + studentId));
    }

    private Account getAuthenticatedAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new InvalidDataException("Thiếu thông tin xác thực");
        }

        return accountRepository.findByUsernameAndDeletedFalse(authentication.getName())
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy tài khoản với username: " + authentication.getName()));
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
