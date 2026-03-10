package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecializationRequest {

    @NotNull(message = "Major ID is required")
    private Integer majorId;

    @NotBlank(message = "Specialization name is required")
    @Size(max = 255, message = "Specialization name must not exceed 255 characters")
    private String specializationName;
}

