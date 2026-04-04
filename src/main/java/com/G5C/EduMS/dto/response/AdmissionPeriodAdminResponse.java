package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.AdmissionPeriodStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionPeriodAdminResponse {
    private Integer id;
    private String periodName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AdmissionPeriodStatus status;

    // Thống kê hỗ trợ Dashboard
    private long totalApplications;    // Tổng số hồ sơ đã nộp vào đợt này
    private long approvedApplications; // Số lượng hồ sơ đã được duyệt (APPROVED)
}