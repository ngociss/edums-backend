package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.RegistrationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CourseRegistrationResponse {
    private Integer id;
    private Integer studentId;
    private String studentCode;
    private Integer courseSectionId;
    private String sectionCode;
    private Integer courseId;
    private String courseCode;
    private String courseName;
    private Integer semesterId;
    private Integer registrationPeriodId;
    private LocalDateTime registrationTime;
    private RegistrationStatus status;
}
