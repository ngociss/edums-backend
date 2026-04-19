package com.G5C.EduMS.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeEntryComponentResponse {

    private Integer componentId;
    private String componentName;
    private Float weightPercentage;
}
