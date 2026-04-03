package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.request.RegistrationPeriodRequest;
import com.G5C.EduMS.dto.response.RegistrationPeriodResponse;
import com.G5C.EduMS.model.RegistrationPeriod;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RegistrationPeriodMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    RegistrationPeriod toEntity(RegistrationPeriodRequest request);

    @Mapping(source = "semester.id", target = "semesterId")
    @Mapping(source = "semester.semesterNumber", target = "semesterNumber")
    @Mapping(source = "semester.academicYear", target = "academicYear")
    RegistrationPeriodResponse toResponse(RegistrationPeriod registrationPeriod);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateEntity(RegistrationPeriodRequest request, @MappingTarget RegistrationPeriod registrationPeriod);
}
