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
    private Integer courseSectionId;
    private Integer registrationPeriodId;
    private LocalDateTime registeredAt;
    private RegistrationStatus status;
}