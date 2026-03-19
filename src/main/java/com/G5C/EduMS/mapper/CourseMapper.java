package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.request.CourseRequest;
import com.G5C.EduMS.dto.response.CourseResponse;
import com.G5C.EduMS.model.Course;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "faculty", ignore = true)
    @Mapping(target = "prerequisiteCourse", ignore = true)
    @Mapping(target = "dependentCourses", ignore = true)
    @Mapping(target = "courseSections", ignore = true)
    @Mapping(target = "gradeComponents", ignore = true)
    @Mapping(target = "courseCode", source = "courseCode")
    @Mapping(target = "courseName", source = "courseName")
    @Mapping(target = "credits", source = "credits")
    @Mapping(target = "status", source = "status")
    Course toEntity(CourseRequest request);

    @Mapping(target = "facultyId", source = "faculty.id")
    @Mapping(target = "facultyName", source = "faculty.facultyName")
    @Mapping(target = "prerequisiteCourseId", source = "prerequisiteCourse.id")
    @Mapping(target = "prerequisiteCourseName", source = "prerequisiteCourse.courseName")
    CourseResponse toResponse(Course course);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "faculty", ignore = true)
    @Mapping(target = "prerequisiteCourse", ignore = true)
    @Mapping(target = "dependentCourses", ignore = true)
    @Mapping(target = "courseSections", ignore = true)
    @Mapping(target = "gradeComponents", ignore = true)

    void updateEntity(CourseRequest request, @MappingTarget Course course);
}

