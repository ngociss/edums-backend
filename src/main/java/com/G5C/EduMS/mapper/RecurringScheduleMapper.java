package com.G5C.EduMS.mapper;

import com.G5C.EduMS.common.enums.DayOfWeek;
import com.G5C.EduMS.common.enums.SchoolPeriod;
import com.G5C.EduMS.dto.response.RecurringScheduleResponse;
import com.G5C.EduMS.model.RecurringSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
}

