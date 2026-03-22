package com.G5C.EduMS.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProfileResponse {
    private String username;
    private String role;
    private String fullName;
    private String email;
    private String phone;
    private String nationalId;
    private String address;
    private LocalDate dateOfBirth;

    // Thuộc tính riêng cho sinh viên
    private String studentCode;
    private String majorName;
}