package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.RegistrationPeriodStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationPeriodResponse {

    private Integer id;
    private Integer semesterId;
    private Integer semesterNumber;
    private String academicYear;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private RegistrationPeriodStatus status;
    private LocalDateTime createdAt;
}
