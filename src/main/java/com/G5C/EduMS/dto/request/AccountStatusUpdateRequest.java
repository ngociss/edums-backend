package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.AccountStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountStatusUpdateRequest {

    @NotNull(message = "Trạng thái cập nhật không được để trống")
    private AccountStatus status;

}