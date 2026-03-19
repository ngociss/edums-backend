package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.request.ClassroomRequest;
import com.G5C.EduMS.dto.response.ClassroomResponse;
import com.G5C.EduMS.model.Classroom;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "recurringSchedules", ignore = true)
    @Mapping(target = "classSessions", ignore = true)
    Classroom toEntity(ClassroomRequest request);

    ClassroomResponse toResponse(Classroom classroom);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "recurringSchedules", ignore = true)
    @Mapping(target = "classSessions", ignore = true)
    void updateEntity(ClassroomRequest request, @MappingTarget Classroom classroom);
}

