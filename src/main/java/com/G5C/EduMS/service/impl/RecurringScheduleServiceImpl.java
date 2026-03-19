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

        LocalDate current = startDate;
        while (!current.isAfter(endDate) && current.getDayOfWeek() != targetDow) {
            current = current.plusDays(1);
        }

        List<ClassSession> sessions = new ArrayList<>();
        while (!current.isAfter(endDate)) {
            final LocalDate sessionDate = current;

            if (!classSessionRepository.existsBySectionIdAndSessionDateAndDeletedFalse(
                    schedule.getSection().getId(), sessionDate)) {
                sessions.add(ClassSession.builder()
                        .section(schedule.getSection())
                        .room(schedule.getRoom())
                        .recurringSchedule(schedule)
                        .sessionDate(sessionDate)
                        .startPeriod(schedule.getStartPeriod())
                        .endPeriod(schedule.getEndPeriod())
                        .status(SessionStatus.NORMAL)
                        .deleted(false)
                        .build());
            }
            current = current.plusWeeks(1);
        }

        if (!sessions.isEmpty()) {
            classSessionRepository.saveAll(sessions);
        }
    }
}

