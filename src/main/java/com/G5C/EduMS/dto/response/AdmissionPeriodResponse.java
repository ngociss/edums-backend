package com.G5C.EduMS.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionPeriodResponse {
    private Integer id; //
    private String periodName; //
    private LocalDateTime startTime; //
    private LocalDateTime endTime; //
    private Integer status; // 1: Active, 0: Closed
}