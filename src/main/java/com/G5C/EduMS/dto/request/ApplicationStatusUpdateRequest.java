package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatusUpdateRequest {

    @NotBlank(message = "Trạng thái không được để trống")
    @Pattern(regexp = "^(APPROVED|REJECTED|PENDING)$", message = "Trạng thái không hợp lệ")
    private ApplicationStatus status; //

    private String note; // Lý do nếu từ chối
}