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
public class MajorRequest {

    @NotNull(message = "Faculty ID is required")
    private Integer facultyId;

    @NotBlank(message = "Major name is required")
    @Size(max = 255, message = "Major name must not exceed 255 characters")
    private String majorName;

    @NotBlank(message = "Major code is required")
    @Size(max = 50, message = "Major code must not exceed 50 characters")
    private String majorCode;
}

