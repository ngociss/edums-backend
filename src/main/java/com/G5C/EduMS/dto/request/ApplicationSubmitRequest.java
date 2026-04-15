package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationSubmitRequest {

    @NotBlank(message = "Họ và tên không được để trống")
    @Size(max = 255, message = "Họ và tên quá dài")
    private String fullName;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    private LocalDate dateOfBirth;

    @NotNull(message = "Vui lòng chọn giới tính")
    private Boolean gender;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Số điện thoại thí sinh không được để trống")
    @Pattern(regexp = "^(0[3|5|7|8|9])+([0-9]{8})$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @NotBlank(message = "Số điện thoại phụ huynh không được để trống")
    @Pattern(regexp = "^(0[3|5|7|8|9])+([0-9]{8})$", message = "Số điện thoại không hợp lệ")
    private String guardianPhone;

    @NotBlank(message = "Số CCCD không được để trống")
    @Pattern(regexp = "^[0-9]{12}$", message = "Căn cước công dân phải bao gồm đúng 12 chữ số")
    private String nationalId;

    @NotBlank(message = "Địa chỉ liên hệ không được để trống")
    private String address;

    @NotNull(message = "Vui lòng chọn Đợt tuyển sinh")
    private Integer periodId;

    @NotNull(message = "Vui lòng chọn Ngành học")
    private Integer majorId;

    @NotNull(message = "Vui lòng chọn Khối xét tuyển")
    private Integer blockId;

    @NotNull(message = "Vui lòng nhập Tổng điểm xét tuyển")
    @DecimalMin(value = "0.0", message = "Điểm không được nhỏ hơn 0")
    @DecimalMax(value = "30.0", message = "Điểm tối đa là 30")
    private Float totalScore;
}