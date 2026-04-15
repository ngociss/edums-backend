package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {
    private Integer id;
    private Integer accountId;
    private String username;

    private Integer facultyId;
    private String facultyName;

    private Integer classId;
    private String className;
    private Integer classMaxCapacity;
    private Integer cohortId;
    private String cohortName;

    private Integer majorId;
    private String majorName;
    private String majorCode;

    private Integer specializationId;
    private String specializationName;

    private Integer guardianId;
    private String guardianName;

    private String studentCode;
    private String fullName;
    private String email;
    private String nationalId;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private Boolean gender;
    private String ethnicity;
    private String religion;
    private String placeOfBirth;
    private String nationality;
    private StudentStatus status;
}
