package com.G5C.EduMS.dto.reponse;

import com.G5C.EduMS.common.enums.SessionStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSessionResponse {

    private Integer id;

    private Integer sectionId;
    private String sectionCode;

    private Integer classroomId;
    private String classroomName;

    private Integer recurringScheduleId;

    private LocalDate sessionDate;
    private Integer startPeriod;
    private Integer endPeriod;
    private String lessonContent;
    private SessionStatus status;
}

