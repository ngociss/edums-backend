package com.G5C.EduMS.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProfileResponse {
    private String username;
    private String role;
    private String fullName;
    private String email;
    private String phone;
    private String nationalId;
    private String address;
    private LocalDate dateOfBirth;

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
}
