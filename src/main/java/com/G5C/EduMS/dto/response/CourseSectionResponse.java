package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.CourseSectionStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseSectionResponse {

    private Integer id;
    private String sectionCode;
    private String displayName;

    private Integer courseId;
    private String courseName;
    private String courseCode;

    private Integer lecturerId;
    private String lecturerName;

    private Integer semesterId;
    private Integer semesterNumber;
    private String academicYear;


    private Integer maxCapacity;
    private CourseSectionStatus status;
    private LocalDateTime createdAt;
}

