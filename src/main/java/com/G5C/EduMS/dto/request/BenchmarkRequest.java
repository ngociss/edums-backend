package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenchmarkRequest {

    @NotNull(message = "ID Ngành học không được để trống")
    private Integer majorId;

    @NotNull(message = "ID Khối xét tuyển không được để trống")
    private Integer blockId;

    @NotNull(message = "ID Đợt tuyển sinh không được để trống")
    private Integer periodId;

    @NotNull(message = "Điểm chuẩn không được để trống")
    @DecimalMin(value = "0.0", message = "Điểm chuẩn nhỏ nhất là 0")
    @DecimalMax(value = "30.0", message = "Điểm chuẩn lớn nhất là 30")
    private Float score;
}