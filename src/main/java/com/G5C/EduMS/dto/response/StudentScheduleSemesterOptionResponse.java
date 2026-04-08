package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.RegistrationPeriodStatus;
import com.G5C.EduMS.common.enums.SemesterStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentScheduleSemesterOptionResponse {

    private Integer semesterId;
    private Integer semesterNumber;
    private String academicYear;
    private String displayName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalWeeks;
    private SemesterStatus semesterStatus;
    private boolean selectableForSchedule;
    private boolean registrationOpen;

    private Integer registrationPeriodId;
    private String registrationPeriodName;
    private RegistrationPeriodStatus registrationPeriodStatus;
    private LocalDateTime registrationStartTime;
    private LocalDateTime registrationEndTime;
}
