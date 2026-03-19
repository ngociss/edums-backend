package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.CourseStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponse {

    private Integer id;
    private String courseCode;
    private String courseName;
    private Integer credits;
    private Integer facultyId;
    private String facultyName;
    private Integer prerequisiteCourseId;
    private String prerequisiteCourseName;
    private CourseStatus status;
}

