package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnboardingRequest {

    @NotNull(message = "Vui lòng chọn Đợt tuyển sinh cần chốt")
    private Integer periodId;

    @NotNull(message = "Vui lòng chọn Khóa học (Cohort) để xếp lớp cho sinh viên mới")
    private Integer cohortId; // Ví dụ: ID của khóa K2024

}