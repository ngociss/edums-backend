package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.CourseSectionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturerCourseSectionResponse {

    private Integer sectionId;
    private String sectionCode;
    private String displayName;

    private Integer courseId;
    private String courseCode;
    private String courseName;

    private Integer semesterId;
    private Integer semesterNumber;
    private String academicYear;

    private Integer maxCapacity;
    private Long registeredStudentCount;
    private CourseSectionStatus status;
}
