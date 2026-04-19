package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GradeDetailRequest {

    @NotNull(message = "Mã thành phần điểm là bắt buộc")
    private Integer componentId;

    @NotNull(message = "Điểm số là bắt buộc")
    @Min(value = 0, message = "Điểm số phải lớn hơn hoặc bằng 0")
    @Max(value = 10, message = "Điểm số phải nhỏ hơn hoặc bằng 10")
    private Float score;
}
