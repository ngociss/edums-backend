package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.CourseSectionStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseSectionRequest {

    @NotBlank(message = "Section code is required")
    @Size(max = 50, message = "Section code must not exceed 50 characters")
    private String sectionCode;

    @Size(max = 255, message = "Display name must not exceed 255 characters")
    private String displayName; // optional

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

