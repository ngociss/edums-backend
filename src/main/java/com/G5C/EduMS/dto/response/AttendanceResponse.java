package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.AttendanceStatus;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AttendanceResponse {

    private Integer id;

    private Integer sessionId;
    private LocalDate sessionDate;

    private Integer courseRegistrationId;
    private Integer studentId;
    private String studentName;
    private String studentCode;

    private AttendanceStatus status;
    private String note;
}
