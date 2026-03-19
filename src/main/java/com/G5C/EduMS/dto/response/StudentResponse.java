package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.StudentStatus;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {
    private Integer id;
    private Integer accountId;

    // Flatten data (Dữ liệu làm phẳng từ các bảng liên kết)
    private Integer classId;
    private String className;
    private Integer majorId;
    private String majorName;
    private Integer specializationId;
    private String specializationName;
    private Integer guardianId;
    private String guardianName;

    // Thông tin chính
    private String studentCode;
    private String fullName;
    private String email;
    private String nationalId;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private Boolean gender;
    private String ethnicity;
    private String religion;
    private String placeOfBirth;
    private String nationality;
    private StudentStatus status;
}