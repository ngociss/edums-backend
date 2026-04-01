package com.G5C.EduMS.dto.response;

import lombok.*;

import java.time.LocalDate;
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

    private Integer semesterId;
    private Integer semesterNumber;
    private String academicYear;
    private LocalDate semesterStartDate;
    private LocalDate semesterEndDate;

    private Integer startWeek;
    private Integer endWeek;
    private LocalDate effectiveStartDate;
    private LocalDate effectiveEndDate;

    private LocalDateTime createdAt;
}

