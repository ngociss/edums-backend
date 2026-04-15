package com.G5C.EduMS.mapper;

import com.G5C.EduMS.common.enums.DayOfWeek;
import com.G5C.EduMS.common.enums.SchoolPeriod;
import com.G5C.EduMS.dto.response.RecurringScheduleResponse;
import com.G5C.EduMS.model.RecurringSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
@Mapper(componentModel = "spring")
public interface RecurringScheduleMapper {

    @Mapping(target = "sectionId",        source = "section.id")
    @Mapping(target = "sectionCode",      source = "section.sectionCode")
    @Mapping(target = "sectionDisplayName", source = "section.displayName")
    @Mapping(target = "classroomId",      source = "room.id")
    @Mapping(target = "classroomName",    source = "room.roomName")
    @Mapping(target = "dayOfWeekName",    expression = "java(getDayName(schedule.getDayOfWeek()))")
    @Mapping(target = "startPeriodTime",  expression = "java(getPeriodTime(schedule.getStartPeriod()))")
    @Mapping(target = "endPeriodTime",    expression = "java(getPeriodTime(schedule.getEndPeriod()))")
    @Mapping(target = "semesterId",       source = "section.semester.id")
    @Mapping(target = "semesterNumber",   source = "section.semester.semesterNumber")
    @Mapping(target = "academicYear",     source = "section.semester.academicYear")
    @Mapping(target = "semesterStartDate", source = "section.semester.startDate")
    @Mapping(target = "semesterEndDate",   source = "section.semester.endDate")
    @Mapping(target = "startWeek", expression = "java(resolveStartWeek(schedule))")
    @Mapping(target = "endWeek", expression = "java(resolveEndWeek(schedule))")
    @Mapping(target = "startDate", expression = "java(resolveEffectiveDate(schedule, resolveStartWeek(schedule)))")
    @Mapping(target = "endDate", expression = "java(resolveEffectiveDate(schedule, resolveEndWeek(schedule)))")
    @Mapping(target = "effectiveStartDate", expression = "java(resolveEffectiveDate(schedule, resolveStartWeek(schedule)))")
    @Mapping(target = "effectiveEndDate", expression = "java(resolveEffectiveDate(schedule, resolveEndWeek(schedule)))")
    RecurringScheduleResponse toResponse(RecurringSchedule schedule);

    default String getDayName(Integer dayOfWeek) {
        if (dayOfWeek == null) return null;
        try {
            return DayOfWeek.fromValue(dayOfWeek).getDisplayName();
        } catch (IllegalArgumentException e) {
            return "Không xác định";
        }
    }

    default String getPeriodTime(Integer period) {
        if (period == null) return null;
        return SchoolPeriod.getTimeRange(period);
    }

    default LocalDate resolveEffectiveDate(RecurringSchedule schedule, Integer weekNumber) {
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
        java.time.DayOfWeek targetDay = DayOfWeek.fromValue(schedule.getDayOfWeek()).toJavaDayOfWeek();

        long daysOffset = targetDay.getValue() - weekStart.getDayOfWeek().getValue();
        if (daysOffset < 0) {
            daysOffset += 7;
        }

        return weekStart.plusDays(daysOffset);
    }

    default Integer resolveStartWeek(RecurringSchedule schedule) {
        if (schedule == null || schedule.getStartWeek() == null) {
            return 1;
        }
        return schedule.getStartWeek();
    }

    default Integer resolveEndWeek(RecurringSchedule schedule) {
        if (schedule == null) {
            return null;
        }
        if (schedule.getEndWeek() != null) {
            return schedule.getEndWeek();
        }
        if (schedule.getSection() != null
                && schedule.getSection().getSemester() != null
                && schedule.getSection().getSemester().getTotalWeeks() != null) {
            return schedule.getSection().getSemester().getTotalWeeks();
        }
        return resolveStartWeek(schedule);
    }
}

