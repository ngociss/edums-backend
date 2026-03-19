package com.G5C.EduMS.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturerResponse {
    private Integer id;
    private Integer accountId;
    private String fullName;
    private String academicDegree;
    private String email;
    private String phone;
}