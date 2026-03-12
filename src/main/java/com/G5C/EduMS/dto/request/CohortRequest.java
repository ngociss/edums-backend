package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.CohortStatus;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CohortRequest {

    @NotBlank(message = "Cohort name is required")
    @Size(max = 255, message = "Cohort name must not exceed 255 characters")
    private String cohortName;

    @NotNull(message = "Start year is required")
    @Min(value = 2000, message = "Start year must be from 2000 onwards")
    @Max(value = 2100, message = "Start year is invalid")
    private Integer startYear;

    @NotNull(message = "End year is required")
    @Min(value = 2000, message = "End year must be from 2000 onwards")
    @Max(value = 2100, message = "End year is invalid")
    private Integer endYear;

    private CohortStatus status;
}
