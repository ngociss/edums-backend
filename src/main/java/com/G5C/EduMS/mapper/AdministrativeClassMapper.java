package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.response.AdministrativeClassResponse;
import com.G5C.EduMS.dto.request.AdministrativeClassRequest;
import com.G5C.EduMS.model.AdministrativeClass;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AdministrativeClassMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "headLecturer", ignore = true)
    @Mapping(target = "cohort", ignore = true)
    @Mapping(target = "major", ignore = true)
    @Mapping(target = "students", ignore = true)
    AdministrativeClass toEntity(AdministrativeClassRequest request);

    @Mapping(target = "headLecturerId", source = "headLecturer.id")
    @Mapping(target = "headLecturerName", source = "headLecturer.fullName")
    @Mapping(target = "cohortId", source = "cohort.id")
    @Mapping(target = "cohortName", source = "cohort.cohortName")
    @Mapping(target = "majorId", source = "major.id")
    @Mapping(target = "majorName", source = "major.majorName")
    AdministrativeClassResponse toResponse(AdministrativeClass entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "headLecturer", ignore = true)
    @Mapping(target = "cohort", ignore = true)
    @Mapping(target = "major", ignore = true)
    @Mapping(target = "students", ignore = true)
    void updateEntity(AdministrativeClassRequest request, @MappingTarget AdministrativeClass entity);
}

