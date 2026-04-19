package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassroomRequest {

    @NotBlank(message = "Tên phòng là bắt buộc")
    @Size(max = 255, message = "Tên phòng không được vượt quá 255 ký tự")
    private String roomName;

    @NotNull(message = "Sức chứa là bắt buộc")
    @Min(value = 1, message = "Sức chứa phải lớn hơn hoặc bằng 1")
    private Integer capacity;

    @NotBlank(message = "Loại phòng là bắt buộc")
    @Size(max = 255, message = "Loại phòng không được vượt quá 255 ký tự")
    private String roomType;
}

