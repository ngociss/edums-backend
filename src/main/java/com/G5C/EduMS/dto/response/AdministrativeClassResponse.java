package com.G5C.EduMS.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AdministrativeClassResponse {

    private Integer id;
    private String className;
    private Integer maxCapacity;

    private Integer headLecturerId;
    private String headLecturerName;

    private Integer cohortId;
    private String cohortName;

    private Integer majorId;
    private String majorName;
}
