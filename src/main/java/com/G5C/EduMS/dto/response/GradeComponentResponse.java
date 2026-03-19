package com.G5C.EduMS.dto.response;

import lombok.Data;

@Data
public class GradeComponentResponse {
    private Integer id;
    private String componentName;
    private Float weightPercentage;
    private Integer courseId;
}
