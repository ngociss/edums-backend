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
}

