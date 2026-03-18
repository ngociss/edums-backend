package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuardianCreateRequest {
    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 50)
    private String fullName;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0|\\+84)[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @Size(max = 50)
    private String relationship; // e.g., Father, Mother, Guardian
}