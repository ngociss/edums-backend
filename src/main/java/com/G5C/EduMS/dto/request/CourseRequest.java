package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.CourseStatus;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequest {

    @NotBlank(message = "Course code is required")
    @Size(max = 20, message = "Course code must not exceed 20 characters")
    private String courseCode;

    @NotBlank(message = "Course name is required")
    @Size(max = 255, message = "Course name must not exceed 255 characters")
    private String courseName;

    @NotNull(message = "Credits is required")
    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 10, message = "Credits must not exceed 10")
    private Integer credits;

    @NotNull(message = "Faculty ID is required")
    private Integer facultyId;

    // null = không có môn tiên quyết
    private Integer prerequisiteCourseId;

    private CourseStatus status = CourseStatus.ACTIVE;
}

