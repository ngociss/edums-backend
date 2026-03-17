package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.reponse.ClassSessionResponse;
import com.G5C.EduMS.model.ClassSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClassSessionMapper {

    @Mapping(target = "sectionId",            source = "section.id")
    @Mapping(target = "sectionCode",          source = "section.sectionCode")
    @Mapping(target = "classroomId",          source = "room.id")
    @Mapping(target = "classroomName",        source = "room.roomName")
    @Mapping(target = "recurringScheduleId",  source = "recurringSchedule.id")
    ClassSessionResponse toResponse(ClassSession session);
}

