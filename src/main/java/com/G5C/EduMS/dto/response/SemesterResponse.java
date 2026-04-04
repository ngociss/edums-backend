package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.SemesterStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterResponse {

    private Integer id;
    private Integer semesterNumber;
    private String academicYear;
    private String displayName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalWeeks;
    private SemesterStatus status;
}
