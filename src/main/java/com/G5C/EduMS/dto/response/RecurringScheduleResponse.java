package com.G5C.EduMS.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecurringScheduleResponse {

    private Integer id;

    private Integer sectionId;
    private String sectionCode;
    private String sectionDisplayName;

    private Integer classroomId;
    private String classroomName;

    /** 1 = Thứ Hai, 7 = Chủ Nhật */
    private Integer dayOfWeek;
    private String dayOfWeekName;

    private Integer startPeriod;
    private String startPeriodTime;

    private Integer endPeriod;
    private String endPeriodTime;

    private LocalDateTime createdAt;
}

