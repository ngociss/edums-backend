package com.G5C.EduMS.dto.reponse;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecializationResponse {

    private Integer id;
    private Integer majorId;
    private String majorName;
    private String specializationName;
}

