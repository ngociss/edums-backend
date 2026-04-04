package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.request.SemesterRequest;
import com.G5C.EduMS.dto.response.SemesterResponse;
import com.G5C.EduMS.model.Semester;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SemesterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "courseSections", ignore = true)
    Semester toEntity(SemesterRequest request);

    @Mapping(target = "displayName", source = ".", qualifiedByName = "toDisplayName")
    SemesterResponse toResponse(Semester semester);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "courseSections", ignore = true)
    void updateEntity(SemesterRequest request, @MappingTarget Semester semester);

    @Named("toDisplayName")
    default String toDisplayName(Semester semester) {
        if (semester == null || semester.getSemesterNumber() == null || semester.getAcademicYear() == null) {
            return null;
        }
        return "Học kỳ " + semester.getSemesterNumber() + " - " + semester.getAcademicYear();
    }
}
