package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.reponse.LecturerScheduleResponse;
import com.G5C.EduMS.model.ClassSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {

    @Mapping(source = "id", target = "sessionId")
    @Mapping(source = "room.roomName", target = "roomName")
    @Mapping(source = "section.sectionCode", target = "sectionCode")
    @Mapping(source = "section.course.courseCode", target = "courseCode")
    @Mapping(source = "section.course.courseName", target = "courseName")
    LecturerScheduleResponse toLecturerScheduleResponse(ClassSession classSession);

    List<LecturerScheduleResponse> toLecturerScheduleResponseList(List<ClassSession> classSessions);
}
