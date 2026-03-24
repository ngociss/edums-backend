package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRegistrationRequest {

    @NotNull(message = "Course section id must not be null")
    private Integer courseSectionId;

    private Integer studentId;
}
