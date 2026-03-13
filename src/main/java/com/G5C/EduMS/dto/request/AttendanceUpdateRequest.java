package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AttendanceUpdateRequest {

    @NotNull(message = "Attendance status is required")
    private AttendanceStatus status;

    private String note;
}
