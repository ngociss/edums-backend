package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.AttendanceStatus;
import com.G5C.EduMS.common.enums.SectionRosterStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionAttendanceRosterResponse {

    private Integer rosterId;
    private Integer sectionId;
    private Integer sessionId;
    private LocalDate sessionDate;

    private Integer studentId;
    private String studentCode;
    private String studentName;

    private Integer courseRegistrationId;
    private Integer attendanceId;
    private AttendanceStatus attendanceStatus;
    private String note;
    private SectionRosterStatus rosterStatus;
}
