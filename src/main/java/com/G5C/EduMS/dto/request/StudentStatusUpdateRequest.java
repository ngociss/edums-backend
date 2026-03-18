package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.StudentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentStatusUpdateRequest {
    @NotNull(message = "Trạng thái không được để trống")
    private StudentStatus status;
}