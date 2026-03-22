package com.G5C.EduMS.dto.response;

import com.G5C.EduMS.common.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicLookupResponse {

    private String fullName;
    private String nationalId;
    private ApplicationStatus status;

    private String periodName;
    private String majorName;
    private String blockName;
    private Float totalScore;
}