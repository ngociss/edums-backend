package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.GradeStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GradeReportRequest {

    @NotNull(message = "Registration ID is required")
    private Integer registrationId;

    @NotEmpty(message = "Grade details must not be empty")
    @Valid
    private List<GradeDetailRequest> gradeDetails;

    private GradeStatus status;
}
