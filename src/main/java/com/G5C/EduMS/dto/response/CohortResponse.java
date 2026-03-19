package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.CohortStatus;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CohortResponse {
    private Integer id;
    private String cohortName;
    private Integer startYear;
    private Integer endYear;
    private CohortStatus status;
}
