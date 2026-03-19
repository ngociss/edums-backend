package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.response.CourseRegistrationResponse;
import com.G5C.EduMS.model.CourseRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseRegistrationMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "section.id", target = "courseSectionId")
    @Mapping(source = "registrationPeriod.id", target = "registrationPeriodId")
    CourseRegistrationResponse toResponse(CourseRegistration courseRegistration);
}