package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.DayOfWeek;
import com.G5C.EduMS.common.enums.SessionStatus;
import com.G5C.EduMS.dto.response.ClassSessionResponse;
import com.G5C.EduMS.dto.response.RecurringScheduleResponse;
import com.G5C.EduMS.dto.request.RecurringScheduleRequest;
import com.G5C.EduMS.exception.CannotDeleteException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.ClassSessionMapper;
import com.G5C.EduMS.mapper.RecurringScheduleMapper;
import com.G5C.EduMS.model.ClassSession;
import com.G5C.EduMS.model.Classroom;
import com.G5C.EduMS.model.CourseSection;
import com.G5C.EduMS.model.RecurringSchedule;
import com.G5C.EduMS.model.Semester;
import com.G5C.EduMS.repository.ClassroomRepository;
import com.G5C.EduMS.repository.ClassSessionRepository;
import com.G5C.EduMS.repository.CourseSectionRepository;
import com.G5C.EduMS.repository.RecurringScheduleRepository;
import com.G5C.EduMS.service.RecurringScheduleService;
import com.G5C.EduMS.validator.RecurringScheduleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecurringScheduleServiceImpl implements RecurringScheduleService {

    private final RecurringScheduleRepository recurringScheduleRepository;
    private final ClassSessionRepository classSessionRepository;
    private final CourseSectionRepository courseSectionRepository;
    private final ClassroomRepository classroomRepository;
    private final RecurringScheduleMapper recurringScheduleMapper;
    private final ClassSessionMapper classSessionMapper;
    private final RecurringScheduleValidator validator;

    // ==================== GET ====================

    @Override
    public List<RecurringScheduleResponse> getBySectionId(Integer sectionId) {
        courseSectionRepository.findByIdAndDeletedFalse(sectionId)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy lớp học phần với id: " + sectionId));
        return recurringScheduleRepository.findAllBySectionIdAndDeletedFalse(sectionId)
                .stream().map(recurringScheduleMapper::toResponse).toList();
    }

    @Override
    public RecurringScheduleResponse getById(Integer id) {
        return recurringScheduleMapper.toResponse(
                recurringScheduleRepository.findByIdAndDeletedFalse(id)
                        .orElseThrow(() -> new NotFoundResourcesException(
                                "Không tìm thấy lịch học với id: " + id)));
    }

    // ==================== CREATE ====================

    @Override
    @Transactional
    public RecurringScheduleResponse create(RecurringScheduleRequest request) {

        validator.validatePeriodLogic(request.getStartPeriod(), request.getEndPeriod());

        CourseSection section = courseSectionRepository.findByIdAndDeletedFalse(request.getSectionId())
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy lớp học phần với id: " + request.getSectionId()));
        validator.validateCourseSection(section);
        int startWeek = resolveStartWeek(request, section.getSemester());
        int endWeek = resolveEndWeek(request, section.getSemester());
        validator.validateWeekRange(section.getSemester(), startWeek, endWeek);

        Classroom classroom = classroomRepository.findByIdAndDeletedFalse(request.getClassroomId())
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy phòng học với id: " + request.getClassroomId()));
        validator.validateClassroomCapacity(section, classroom);

        validator.validateConflicts(
                section.getId(),
                classroom.getId(),
                section.getLecturer().getId(),
                section.getSemester().getId(),
                request.getDayOfWeek(),
                request.getStartPeriod(),
                request.getEndPeriod(),
                0
        );

        RecurringSchedule schedule = RecurringSchedule.builder()
                .section(section)
                .room(classroom)
                .dayOfWeek(request.getDayOfWeek())
                .startPeriod(request.getStartPeriod())
                .endPeriod(request.getEndPeriod())
                .startWeek(startWeek)
                .endWeek(endWeek)
                .deleted(false)
                .build();

        schedule = recurringScheduleRepository.save(schedule);

        generateClassSessions(schedule);

        return recurringScheduleMapper.toResponse(schedule);
    }

    // ==================== UPDATE ====================

    @Override
    @Transactional
    public RecurringScheduleResponse update(Integer id, RecurringScheduleRequest request) {
        RecurringSchedule schedule = recurringScheduleRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy lịch học với id: " + id));

        validator.validatePeriodLogic(request.getStartPeriod(), request.getEndPeriod());

        CourseSection section = courseSectionRepository.findByIdAndDeletedFalse(request.getSectionId())
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy lớp học phần với id: " + request.getSectionId()));
        validator.validateCourseSection(section);
        int startWeek = resolveStartWeek(request, section.getSemester());
        int endWeek = resolveEndWeek(request, section.getSemester());
        validator.validateWeekRange(section.getSemester(), startWeek, endWeek);

        Classroom classroom = classroomRepository.findByIdAndDeletedFalse(request.getClassroomId())
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy phòng học với id: " + request.getClassroomId()));
        validator.validateClassroomCapacity(section, classroom);

        validator.validateConflicts(
                section.getId(),
                classroom.getId(),
                section.getLecturer().getId(),
                section.getSemester().getId(),
                request.getDayOfWeek(),
                request.getStartPeriod(),
                request.getEndPeriod(),
                id
        );

        List<ClassSession> oldSessions =
                classSessionRepository.findAllByRecurringScheduleIdAndDeletedFalse(id);
        oldSessions.forEach(s -> s.setDeleted(true));
        classSessionRepository.saveAll(oldSessions);

        schedule.setSection(section);
        schedule.setRoom(classroom);
        schedule.setDayOfWeek(request.getDayOfWeek());
        schedule.setStartPeriod(request.getStartPeriod());
        schedule.setEndPeriod(request.getEndPeriod());
        schedule.setStartWeek(startWeek);
        schedule.setEndWeek(endWeek);
        schedule = recurringScheduleRepository.save(schedule);

        generateClassSessions(schedule);

        return recurringScheduleMapper.toResponse(schedule);
    }

    // ==================== DELETE ====================

    @Override
    @Transactional
    public void delete(Integer id) {
        RecurringSchedule schedule = recurringScheduleRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy lịch học với id: " + id));

        // Không được xóa nếu đã có buổi học có điểm danh
        if (classSessionRepository.existsSessionWithAttendanceByScheduleId(id)) {
            throw new CannotDeleteException("SCHEDULE_HAS_ATTENDANCE",
                    "Không thể xóa lịch học vì đã có buổi học được điểm danh. "
                    + "Vui lòng hủy buổi học riêng lẻ thay vì xóa lịch.");
        }

        List<ClassSession> sessions =
                classSessionRepository.findAllByRecurringScheduleIdAndDeletedFalse(id);
        sessions.forEach(s -> s.setDeleted(true));
        classSessionRepository.saveAll(sessions);

        schedule.setDeleted(true);
        recurringScheduleRepository.save(schedule);
    }

    // ==================== GET CLASS SESSIONS ====================

    @Override
    public List<ClassSessionResponse> getClassSessions(Integer scheduleId) {
        recurringScheduleRepository.findByIdAndDeletedFalse(scheduleId)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy lịch học với id: " + scheduleId));
        return classSessionRepository.findAllByRecurringScheduleIdAndDeletedFalse(scheduleId)
                .stream().map(classSessionMapper::toResponse).toList();
    }

    private void generateClassSessions(RecurringSchedule schedule) {
        Semester semester = schedule.getSection().getSemester();
        LocalDate startDate = semester.getStartDate();
        LocalDate endDate   = semester.getEndDate();

        java.time.DayOfWeek targetDow =
                DayOfWeek.fromValue(schedule.getDayOfWeek()).toJavaDayOfWeek();

        List<ClassSession> sessions = new ArrayList<>();
        int startWeek = schedule.getStartWeek() == null ? 1 : schedule.getStartWeek();
        int endWeek = schedule.getEndWeek() == null ? resolveSemesterTotalWeeks(semester) : schedule.getEndWeek();

        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            if (current.getDayOfWeek() == targetDow) {
                int weekNumber = (int) ChronoUnit.WEEKS.between(startDate, current) + 1;
                if (weekNumber >= startWeek && weekNumber <= endWeek) {
                    if (!classSessionRepository.existsByRecurringScheduleIdAndSessionDateAndDeletedFalse(
                            schedule.getId(), current)) {
                        sessions.add(ClassSession.builder()
                                .room(schedule.getRoom())
                                .recurringSchedule(schedule)
                                .sessionDate(current)
                                .startPeriod(schedule.getStartPeriod())
                                .endPeriod(schedule.getEndPeriod())
                                .status(SessionStatus.NORMAL)
                                .deleted(false)
                                .build());
                    }
                }
            }
            current = current.plusDays(1);
        }

        if (!sessions.isEmpty()) {
            classSessionRepository.saveAll(sessions);
        }
    }

    private int resolveStartWeek(RecurringScheduleRequest request, Semester semester) {
        return request.getStartWeek() == null ? 1 : request.getStartWeek();
    }

    private int resolveEndWeek(RecurringScheduleRequest request, Semester semester) {
        if (request.getEndWeek() != null) {
            return request.getEndWeek();
        }
        return resolveSemesterTotalWeeks(semester);
    }

    private int resolveSemesterTotalWeeks(Semester semester) {
        if (semester != null && semester.getTotalWeeks() != null && semester.getTotalWeeks() > 0) {
            return semester.getTotalWeeks();
        }
        if (semester != null && semester.getStartDate() != null && semester.getEndDate() != null) {
            return (int) ChronoUnit.WEEKS.between(semester.getStartDate(), semester.getEndDate()) + 1;
        }
        return 1;
    }
}

