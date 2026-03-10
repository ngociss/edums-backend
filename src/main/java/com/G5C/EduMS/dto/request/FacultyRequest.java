package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacultyRequest {

    @NotBlank(message = "Faculty name is required")
    @Size(max = 255, message = "Faculty name must not exceed 255 characters")
    private String facultyName;

    @NotBlank(message = "Faculty code is required")
    @Size(max = 50, message = "Faculty code must not exceed 50 characters")
    private String facultyCode;
}

