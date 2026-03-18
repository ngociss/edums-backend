package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AttendanceItemRequest {

    @NotNull(message = "Course registration ID is required")
    private Integer courseRegistrationId;

    @NotNull(message = "Attendance status is required")
    private AttendanceStatus status;

    private String note;
}
