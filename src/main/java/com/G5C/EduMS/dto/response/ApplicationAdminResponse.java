package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationAdminResponse {

    // Thông tin cá nhân cơ bản
    private Integer id;
    private String fullName;
    private LocalDate dateOfBirth;
    private String email;
    private String phone;
    private String nationalId;
    private String address;

    // Thông tin xét tuyển
    private Float totalScore;
    private ApplicationStatus status;
    private LocalDateTime approvalDate;

    // Phẳng hóa dữ liệu Đợt tuyển sinh
    private Integer periodId;
    private String periodName;

    // Phẳng hóa dữ liệu Ngành học
    private Integer majorId;
    private String majorName;

    // Phẳng hóa dữ liệu Khối xét tuyển
    private Integer blockId;
    private String blockName;
}