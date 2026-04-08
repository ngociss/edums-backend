package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.CourseSectionStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseSectionRequest {

    @NotNull(message = "Course ID is required")
    private Integer courseId;

    private Integer lecturerId; // optional khi DRAFT

    @NotNull(message = "Semester ID is required")
    private Integer semesterId;


    @NotNull(message = "Max capacity is required")
    @Min(value = 1, message = "Max capacity must be at least 1")
    private Integer maxCapacity;

    private CourseSectionStatus status = CourseSectionStatus.DRAFT;
}

