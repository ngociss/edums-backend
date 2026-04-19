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

    @NotBlank(message = "Tên khoa là bắt buộc")
    @Size(max = 255, message = "Tên khoa không được vượt quá 255 ký tự")
    private String facultyName;

    @NotBlank(message = "Mã khoa là bắt buộc")
    @Size(max = 50, message = "Mã khoa không được vượt quá 50 ký tự")
    private String facultyCode;
}

