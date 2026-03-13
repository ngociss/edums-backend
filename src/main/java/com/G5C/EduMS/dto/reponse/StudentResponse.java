package com.G5C.EduMS.dto.reponse;

import com.G5C.EduMS.common.enums.StudentStatus;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StudentResponse {

    private Integer id;
    private String studentCode;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private Boolean gender;
    private String nationalId;
    private String ethnicity;
    private String religion;
    private String placeOfBirth;
    private String nationality;
    private StudentStatus status;

    private Integer administrativeClassId;
    private String administrativeClassName;

    private Integer specializationId;
    private String specializationName;

    private Integer guardianId;
    private String guardianName;
    private String guardianPhone;
    private String guardianRelationship;
}
