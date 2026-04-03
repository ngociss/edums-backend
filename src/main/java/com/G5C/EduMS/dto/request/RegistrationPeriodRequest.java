package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.RegistrationPeriodStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationPeriodRequest {

    @NotNull(message = "Semester ID is required")
    private Integer semesterId;

    @NotBlank(message = "Registration period name is required")
    @Size(max = 100, message = "Registration period name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    @NotNull(message = "Status is required")
    private RegistrationPeriodStatus status;
}
