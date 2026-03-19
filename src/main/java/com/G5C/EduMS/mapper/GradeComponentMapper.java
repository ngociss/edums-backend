package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.response.GradeComponentResponse;
import com.G5C.EduMS.dto.request.GradeComponentRequest;
import com.G5C.EduMS.model.GradeComponent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GradeComponentMapper {

    @Mapping(target = "course", ignore = true)
    GradeComponent toEntity(GradeComponentRequest request);

    @Mapping(source = "course.id", target = "courseId")
    GradeComponentResponse toResponse(GradeComponent entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "gradeDetails", ignore = true)
    void updateEntityFromRequest(GradeComponentRequest request, @MappingTarget GradeComponent entity);
}
