package com.G5C.EduMS.dto.reponse;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GradeDetailResponse {

    private Integer id;
    private Integer componentId;
    private String componentName;
    private Float weightPercentage;
    private Float score;
}
