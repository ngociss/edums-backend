package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.response.GradeDetailResponse;
import com.G5C.EduMS.dto.response.GradeReportResponse;
import com.G5C.EduMS.model.GradeDetail;
import com.G5C.EduMS.model.GradeReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GradeReportMapper {

    @Mapping(target = "registrationId", source = "registration.id")
    @Mapping(target = "studentId", source = "registration.student.id")
    @Mapping(target = "studentName", source = "registration.student.fullName")
    @Mapping(target = "studentCode", source = "registration.student.studentCode")
    @Mapping(target = "sectionId", source = "registration.section.id")
    @Mapping(target = "sectionCode", source = "registration.section.sectionCode")
    @Mapping(target = "semesterId", source = "registration.section.semester.id")
    @Mapping(target = "semesterNumber", source = "registration.section.semester.semesterNumber")
    @Mapping(target = "academicYear", source = "registration.section.semester.academicYear")
    @Mapping(target = "courseCode", source = "registration.section.course.courseCode")
    @Mapping(target = "courseName", source = "registration.section.course.courseName")
    @Mapping(target = "gradeDetails", source = "gradeDetails")
    @Mapping(target = "credits", source = "registration.section.course.credits")
    GradeReportResponse toResponse(GradeReport gradeReport);

    @Mapping(target = "componentId", source = "component.id")
    @Mapping(target = "componentName", source = "component.componentName")
    @Mapping(target = "weightPercentage", source = "component.weightPercentage")
    GradeDetailResponse toDetailResponse(GradeDetail gradeDetail);
}
