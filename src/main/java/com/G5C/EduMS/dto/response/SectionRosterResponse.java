package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.SectionRosterStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionRosterResponse {

    private Integer id;
    private Integer sectionId;
    private Integer studentId;
    private String studentCode;
    private String studentName;
    private Integer courseRegistrationId;
    private Integer sourceRegistrationPeriodId;
    private SectionRosterStatus status;
    private LocalDateTime lockedAt;
}
