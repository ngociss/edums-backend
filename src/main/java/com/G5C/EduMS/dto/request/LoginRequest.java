package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank( message = "Tên đăng nhập là bắt buộc")
    private String username;

    @NotBlank( message = "Mật khẩu là bắt buộc")
    private String password;
}
