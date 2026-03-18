package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentUpdateRequest {
    @NotNull(message = "Lớp không được để trống")
    private Integer classId;

    @NotNull(message = "Ngành không được để trống")
    private Integer majorId;

    private Integer specializationId;
    private Integer guardianId;

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "CCCD không được để trống")
    private String nationalId;

    private LocalDate dateOfBirth;
    private Boolean gender;
    private String phone;
    private String address;
    private String ethnicity;
    private String religion;
    private String placeOfBirth;
    private String nationality;
}