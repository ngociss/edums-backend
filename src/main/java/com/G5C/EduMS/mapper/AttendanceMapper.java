package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.response.AttendanceResponse;
import com.G5C.EduMS.model.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mapping(target = "sessionId", source = "session.id")
    @Mapping(target = "sessionDate", source = "session.sessionDate")
    @Mapping(target = "courseRegistrationId", source = "registration.id")
    @Mapping(target = "studentId", source = "registration.student.id")
    @Mapping(target = "studentName", source = "registration.student.fullName")
    @Mapping(target = "studentCode", source = "registration.student.studentCode")
    @Mapping(target = "status", source = "attendanceStatus")
    @Mapping(target = "note", source = "note")
    AttendanceResponse toResponse(Attendance attendance);
}
