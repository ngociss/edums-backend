package com.G5C.EduMS.dto.reponse;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MajorResponse {

    private Integer id;
    private Integer facultyId;
    private String facultyName;
    private String majorName;
    private String majorCode;
}

