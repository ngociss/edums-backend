package com.G5C.EduMS.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CreateAdmissionCampaignRequest {

    // --- 1. THÔNG TIN ĐỢT ---
    @NotBlank(message = "Tên đợt tuyển sinh không được để trống")
    private String periodName;

    @NotNull(message = "Thời gian bắt đầu không được để trống")
    private LocalDateTime startTime;

    @NotNull(message = "Thời gian kết thúc không được để trống")
    private LocalDateTime endTime;

    // --- 2. DANH SÁCH ĐIỂM CHUẨN ---
    @NotEmpty(message = "Phải có ít nhất 1 điểm chuẩn cho đợt tuyển sinh này")
    @Valid
    private List<BenchmarkConfigItem> benchmarks;

    // ==========================================
    // CLASS CON HỨNG DỮ LIỆU ĐIỂM CHUẨN
    // ==========================================
    @Getter
    @Setter
    public static class BenchmarkConfigItem {
        @NotNull(message = "ID Ngành học không được để trống")
        private Integer majorId;

        @NotNull(message = "ID Khối xét tuyển không được để trống")
        private Integer blockId; // Chỉ nhận ID Khối

        @NotNull(message = "Điểm chuẩn không được để trống")
        @DecimalMin(value = "0.0", message = "Điểm chuẩn nhỏ nhất là 0")
        @DecimalMax(value = "40.0", message = "Điểm chuẩn lớn nhất là 40") // Tùy thang điểm của trường
        private Float score;
    }
}