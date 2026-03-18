package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCreateRequest {

    // Khóa ngoại bắt buộc
    @NotNull(message = "Lớp không được để trống")
    private Integer classId;

    @NotNull(message = "Ngành không được để trống")
    private Integer majorId;

    // Khóa ngoại tùy chọn
    private Integer specializationId;
    private Integer guardianId;

    // Thông tin cá nhân bắt buộc
    @NotBlank(message = "Mã sinh viên không được để trống")
    @Size(max = 10, message = "Mã sinh viên tối đa 10 ký tự")
    private String studentCode;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 50, message = "Họ tên tối đa 50 ký tự")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Size(max = 50, message = "Email tối đa 50 ký tự")
    private String email;

    @NotBlank(message = "CCCD không được để trống")
    @Pattern(regexp = "^[0-9]{12}$", message = "CCCD phải đúng 12 số")
    private String nationalId;

    // Thông tin bổ sung (có thể null)
    private LocalDate dateOfBirth;
    private Boolean gender;

    @Pattern(regexp = "^(0|\\+84)[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @Size(max = 250)
    private String address;
    private String ethnicity;
    private String religion;
    private String placeOfBirth;
    private String nationality;
}