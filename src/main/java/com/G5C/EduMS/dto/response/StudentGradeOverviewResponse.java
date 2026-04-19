package com.G5C.EduMS.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentGradeOverviewResponse {

    private List<GradeReportResponse> reports;
    private List<GradeSemesterSummaryResponse> semesterSummaries;
    private Float cumulativeAverage10;
    private Integer cumulativeEarnedCredits;
}
