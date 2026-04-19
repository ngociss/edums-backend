package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AdministrativeClassRequest {

    @NotBlank(message = "Tên lớp là bắt buộc")
    @Size(max = 255, message = "Tên lớp không được vượt quá 255 ký tự")
    private String className;

    @NotNull(message = "Mã giảng viên chủ nhiệm là bắt buộc")
    private Integer headLecturerId; 

    @NotNull(message = "Mã khóa học là bắt buộc")
    private Integer cohortId;

    @NotNull(message = "Mã ngành học là bắt buộc")
    private Integer majorId;

    @NotNull(message = "Sĩ số tối đa là bắt buộc")
    @Min(value = 1, message = "Sĩ số tối đa phải lớn hơn hoặc bằng 1")
    private Integer maxCapacity;
}
