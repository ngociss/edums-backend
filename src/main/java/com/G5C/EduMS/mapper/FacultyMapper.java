package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.request.FacultyRequest;
import com.G5C.EduMS.dto.reponse.FacultyResponse;
import com.G5C.EduMS.model.Faculty;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FacultyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "majors", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Faculty toEntity(FacultyRequest request);

    FacultyResponse toResponse(Faculty faculty);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "majors", ignore = true)
    @Mapping(target = "courses", ignore = true)
    void updateEntity(FacultyRequest request, @MappingTarget Faculty faculty);
}

