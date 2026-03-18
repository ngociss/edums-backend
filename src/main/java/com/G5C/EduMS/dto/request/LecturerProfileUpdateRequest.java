package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LecturerProfileUpdateRequest {
    @Pattern(regexp = "^(0|\\+84)[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @Email(message = "Email không hợp lệ")
    private String email;
}