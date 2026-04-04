package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRegistrationSwitchRequest {

    @NotNull(message = "Mã lớp học phần muốn chuyển đến không được để trống")
    private Integer newCourseSectionId;
}
