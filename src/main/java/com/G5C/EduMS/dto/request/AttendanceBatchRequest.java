package com.G5C.EduMS.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AttendanceBatchRequest {

    @NotEmpty(message = "Danh sách điểm danh không được để trống")
    @Valid
    private List<AttendanceItemRequest> items;
}
