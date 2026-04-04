package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.CourseSectionStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableCourseSectionResponse {

    private Integer courseSectionId;
    private String sectionCode;
    private String displayName;

    private Integer courseId;
    private String courseCode;
    private String courseName;
    private Integer credits;

    private Integer facultyId;
    private String facultyName;

    private Integer prerequisiteCourseId;
    private String prerequisiteCourseCode;
    private String prerequisiteCourseName;

    private Integer lecturerId;
    private String lecturerName;

    private Integer semesterId;
    private Integer semesterNumber;
    private String academicYear;

    private Integer registrationPeriodId;
    private String registrationPeriodName;
    private LocalDateTime registrationStartTime;
    private LocalDateTime registrationEndTime;

    private Integer maxCapacity;
    private long registeredCount;
    private long remainingCapacity;
    private CourseSectionStatus status;

    private List<AvailableCourseSectionScheduleResponse> schedules;
}
