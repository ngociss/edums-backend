package com.G5C.EduMS.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AttendanceBatchRequest {

    @NotEmpty(message = "Attendance list must not be empty")
    @Valid
    private List<AttendanceItemRequest> items;
}
