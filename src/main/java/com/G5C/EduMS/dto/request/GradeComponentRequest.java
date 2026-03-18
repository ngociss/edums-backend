package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GradeComponentRequest {
    
    @NotBlank(message = "Component name is required")
    private String componentName;
    
    @NotNull(message = "Weight percentage is required")
    @Min(value = 0, message = "Weight percentage must be at least 0")
    @Max(value = 100, message = "Weight percentage cannot exceed 100")
    private Float weightPercentage;
    
    @NotNull(message = "Course ID is required")
    private Integer courseId;
}
