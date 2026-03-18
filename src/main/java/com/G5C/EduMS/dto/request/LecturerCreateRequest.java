package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturerCreateRequest {

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 50, message = "Họ tên tối đa 50 ký tự")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = 50, message = "Email tối đa 50 ký tự")
    private String email;

    @Size(max = 50, message = "Học vị tối đa 50 ký tự")
    private String academicDegree; // Ví dụ: ThS, TS, PGS.TS

    @Pattern(regexp = "^(0|\\+84)[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;
}