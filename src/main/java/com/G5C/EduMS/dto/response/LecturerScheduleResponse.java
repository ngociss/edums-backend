package com.G5C.EduMS.dto.response;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturerScheduleResponse {
    private Integer sessionId;
    private LocalDate sessionDate;
    private Integer startPeriod; // Tiết bắt đầu
    private Integer endPeriod;   // Tiết kết thúc
    private String roomName;     // Tên phòng học (từ bảng classrooms)
    private String courseCode;   // Mã môn học (từ bảng courses)
    private String courseName;   // Tên môn học (từ bảng courses)
    private String sectionCode;  // Mã lớp học phần (từ bảng course_sections)
    private String status;       // Trạng thái buổi học (VD: SCHEDULED, COMPLETED, CANCELLED)
}