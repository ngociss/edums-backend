package com.G5C.EduMS.dto.response;

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
public class GradeSemesterSummaryResponse {

    private Integer semesterId;
    private Integer semesterNumber;
    private String academicYear;
    private Float semesterAverage10;
    private Integer semesterEarnedCredits;
}
