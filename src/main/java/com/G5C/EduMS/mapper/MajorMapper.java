package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.request.MajorRequest;
import com.G5C.EduMS.dto.response.MajorResponse;
import com.G5C.EduMS.model.Major;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MajorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "faculty", ignore = true)
    @Mapping(target = "specializations", ignore = true)
    @Mapping(target = "administrativeClasses", ignore = true)
    @Mapping(target = "admissionApplications", ignore = true)
    @Mapping(target = "benchmarkScores", ignore = true)
    Major toEntity(MajorRequest request);

    @Mapping(target = "facultyId", source = "faculty.id")
    @Mapping(target = "facultyName", source = "faculty.facultyName")

    MajorResponse toResponse(Major major);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "faculty", ignore = true)
    @Mapping(target = "specializations", ignore = true)
    @Mapping(target = "administrativeClasses", ignore = true)
    @Mapping(target = "admissionApplications", ignore = true)
    @Mapping(target = "benchmarkScores", ignore = true)
    void updateEntity(MajorRequest request, @MappingTarget Major major);
}
