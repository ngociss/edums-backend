package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.GradeStatus;
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
public class GradeEntryRosterRowResponse {

    private Integer rosterId;
    private Integer studentId;
    private String studentCode;
    private String studentName;
    private Integer courseRegistrationId;
    private Integer gradeReportId;
    private GradeStatus gradeStatus;
    private Float finalScore;
    private String letterGrade;
    private List<GradeDetailResponse> gradeDetails;
}
