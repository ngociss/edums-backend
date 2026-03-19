package com.G5C.EduMS.dto.response;

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

