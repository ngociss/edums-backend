package com.G5C.EduMS.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatusResponse {
    private String fullName; //
    private String status; //
    private String majorName; //
    private Float totalScore; //
    private Float benchmarkScore; // Điểm chuẩn của ngành/khối đó để thí sinh đối chiếu
    private String message; // Thông điệp tùy chỉnh (VD: "Chúc mừng bạn đã trúng tuyển")
}
