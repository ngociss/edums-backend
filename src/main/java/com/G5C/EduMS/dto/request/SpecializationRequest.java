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

    @NotNull(message = "Mã ngành học là bắt buộc")
    private Integer majorId;

    @NotBlank(message = "Tên chuyên ngành là bắt buộc")
    @Size(max = 255, message = "Tên chuyên ngành không được vượt quá 255 ký tự")
    private String specializationName;
}

