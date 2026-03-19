package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.request.SpecializationRequest;
import com.G5C.EduMS.dto.response.SpecializationResponse;
import com.G5C.EduMS.model.Specialization;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SpecializationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "major", ignore = true)
    @Mapping(target = "students", ignore = true)
    Specialization toEntity(SpecializationRequest request);

    @Mapping(target = "majorId", source = "major.id")
    @Mapping(target = "majorName", source = "major.majorName")
    SpecializationResponse toResponse(Specialization specialization);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "major", ignore = true)
    @Mapping(target = "students", ignore = true)
    void updateEntity(SpecializationRequest request, @MappingTarget Specialization specialization);
}

