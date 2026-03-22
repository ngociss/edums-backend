package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.AdmissionPeriodStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeriodRequest {

    @NotBlank(message = "Tên đợt tuyển sinh không được để trống")
    private String periodName;

    @NotNull(message = "Thời gian bắt đầu không được để trống")
    private LocalDateTime startTime;

    @NotNull(message = "Thời gian kết thúc không được để trống")
    private LocalDateTime endTime;

    @NotNull(message = "Trạng thái không được để trống")
    private AdmissionPeriodStatus status;
}