package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationReviewRequest {

    @NotNull(message = "Trạng thái xét duyệt không được để trống")
    private ApplicationStatus status;

    // Có thể lưu note này vào một bảng log xét duyệt hoặc thêm cột note vào bảng hồ sơ nếu cần
    private String note;
}