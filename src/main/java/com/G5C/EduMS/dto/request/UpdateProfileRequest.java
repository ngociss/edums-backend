package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateProfileRequest {

    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    @Pattern(regexp = "^(0[3|5|7|8|9])+([0-9]{8})$", message = "Số điện thoại không hợp lệ")
    private String phone;

    private String address;

    private LocalDate dateOfBirth;
}