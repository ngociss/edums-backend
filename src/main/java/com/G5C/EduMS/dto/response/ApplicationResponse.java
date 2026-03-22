package com.G5C.EduMS.dto.response;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {
    private Integer id; //
    private String fullName; //
    private String nationalId; //
    private String email; //
    private String phone; //
    private LocalDate dateOfBirth; //

    // Thông tin ngành và khối (Dạng chuỗi để hiển thị trực tiếp)
    private String majorName; // Lấy từ bảng majors
    private String blockName; // Lấy từ bảng admission_blocks

    private Float totalScore; //
    private String status; // PENDING, APPROVED, REJECTED
    private LocalDateTime approvalDate; //

    // Minh chứng (Đường dẫn từ Cloudinary)
    private String transcriptUrl;
    private String idCardUrl;
}