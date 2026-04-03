package com.G5C.EduMS.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableCourseSectionScheduleResponse {

    private Integer roomId;
    private String roomName;
    private Integer dayOfWeek;
    private Integer startPeriod;
    private Integer endPeriod;
    private Integer startWeek;
    private Integer endWeek;
    private LocalDate effectiveStartDate;
    private LocalDate effectiveEndDate;
}
