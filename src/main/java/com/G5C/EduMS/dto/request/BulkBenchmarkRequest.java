package com.G5C.EduMS.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkBenchmarkRequest {

    @NotNull(message = "ID Đợt tuyển sinh không được để trống")
    private Integer periodId;

    @NotEmpty(message = "Danh sách điểm chuẩn không được để trống")
    @Valid // Kích hoạt validate cho từng phần tử trong List
    private List<BenchmarkItem> benchmarks;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BenchmarkItem {
        @NotNull(message = "ID Ngành học không được để trống")
        private Integer majorId;

        @NotNull(message = "ID Khối xét tuyển không được để trống")
        private Integer blockId;

        @NotNull(message = "Điểm chuẩn không được để trống")
        private Float score;
    }
}