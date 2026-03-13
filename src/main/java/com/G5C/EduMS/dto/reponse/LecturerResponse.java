package com.G5C.EduMS.dto.reponse;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LecturerResponse {

    private Integer id;
    private String fullName;
    private String academicDegree;
    private String email;
    private String phone;
}
