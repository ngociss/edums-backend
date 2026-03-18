package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GradeDetailRequest {

    @NotNull(message = "Component ID is required")
    private Integer componentId;

    @NotNull(message = "Score is required")
    @Min(value = 0, message = "Score must be at least 0")
    @Max(value = 10, message = "Score must be at most 10")
    private Float score;
}
