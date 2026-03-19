package com.G5C.EduMS.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacultyResponse {

    private Integer id;
    private String facultyName;
    private String facultyCode;
}

