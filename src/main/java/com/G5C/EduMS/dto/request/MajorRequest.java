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

    @NotNull(message = "Mã khoa là bắt buộc")
    private Integer facultyId;

    @NotBlank(message = "Tên ngành học là bắt buộc")
    @Size(max = 255, message = "Tên ngành học không được vượt quá 255 ký tự")
    private String majorName;

    @NotBlank(message = "Mã ngành học là bắt buộc")
    @Size(max = 50, message = "Mã ngành học không được vượt quá 50 ký tự")
    private String majorCode;
}

