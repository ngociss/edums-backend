package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.GradeStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GradeReportResponse {

    private Integer id;
    private Integer registrationId;
    private Integer studentId;
    private String studentName;
    private String studentCode;
    private Integer sectionId;
    private String sectionCode;
    private Integer semesterId;
    private Integer semesterNumber;
    private String academicYear;
    private String courseCode;
    private String courseName;
    private Float finalScore;
    private String letterGrade;
    private GradeStatus status;
    private LocalDateTime createdAt;
    private List<GradeDetailResponse> gradeDetails;
    private Integer credits;
}
