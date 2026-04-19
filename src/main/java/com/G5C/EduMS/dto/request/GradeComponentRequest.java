package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GradeComponentRequest {
    
    @NotBlank(message = "Tên thành phần điểm là bắt buộc")
    private String componentName;
    
    @NotNull(message = "Tỷ trọng phần trăm là bắt buộc")
    @Min(value = 0, message = "Tỷ trọng phần trăm phải lớn hơn hoặc bằng 0")
    @Max(value = 100, message = "Tỷ trọng phần trăm không được vượt quá 100")
    private Float weightPercentage;
    
    @NotNull(message = "Mã môn học là bắt buộc")
    private Integer courseId;
}
