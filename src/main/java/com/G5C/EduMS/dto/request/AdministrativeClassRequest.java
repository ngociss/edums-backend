package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AdministrativeClassRequest {

    @NotBlank(message = "Class name is required")
    @Size(max = 255, message = "Class name must not exceed 255 characters")
    private String className;

    @NotNull(message = "Head lecturer ID is required")
    private Integer headLecturerId; 

    @NotNull(message = "Cohort ID is required")
    private Integer cohortId;

    @NotNull(message = "Major ID is required")
    private Integer majorId;

    @NotNull(message = "Max capacity is required")
    @Min(value = 1, message = "Max capacity must be at least 1")
    private Integer maxCapacity; 
}
