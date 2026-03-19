package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.request.CourseSectionRequest;
import com.G5C.EduMS.dto.response.CourseSectionResponse;
import com.G5C.EduMS.model.CourseSection;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CourseSectionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "lecturer", ignore = true)
    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "recurringSchedules", ignore = true)
    @Mapping(target = "classSessions", ignore = true)
    @Mapping(target = "courseRegistrations", ignore = true)
    @Mapping(target = "sectionCode", source = "sectionCode")
    @Mapping(target = "displayName", source = "displayName")
    @Mapping(target = "maxCapacity", source = "maxCapacity")
    @Mapping(target = "status", source = "status")
    CourseSection toEntity(CourseSectionRequest request);

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "courseName", source = "course.courseName")
    @Mapping(target = "courseCode", source = "course.courseCode")
    @Mapping(target = "lecturerId", source = "lecturer.id")
    @Mapping(target = "lecturerName", source = "lecturer.fullName")
    @Mapping(target = "semesterId", source = "semester.id")
    @Mapping(target = "semesterNumber", source = "semester.semesterNumber")
    @Mapping(target = "academicYear", source = "semester.academicYear")
    CourseSectionResponse toResponse(CourseSection courseSection);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "lecturer", ignore = true)
    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "recurringSchedules", ignore = true)
    @Mapping(target = "classSessions", ignore = true)
    @Mapping(target = "courseRegistrations", ignore = true)
    @Mapping(target = "sectionCode", source = "sectionCode")
    @Mapping(target = "displayName", source = "displayName")
    @Mapping(target = "maxCapacity", source = "maxCapacity")
    @Mapping(target = "status", source = "status")
    void updateEntity(CourseSectionRequest request, @MappingTarget CourseSection courseSection);
}

