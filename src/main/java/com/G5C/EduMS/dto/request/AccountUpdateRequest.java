package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountUpdateRequest {

    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @NotNull(message = "Vai trò (Role ID) không được để trống")
    private Integer roleId;

    private String avatarUrl;
}
