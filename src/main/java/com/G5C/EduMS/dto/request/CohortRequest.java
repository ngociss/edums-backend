package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.CohortStatus;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CohortRequest {

    @NotBlank(message = "Tên khóa học là bắt buộc")
    @Size(max = 255, message = "Tên khóa học không được vượt quá 255 ký tự")
    private String cohortName;

    @NotNull(message = "Năm bắt đầu là bắt buộc")
    @Min(value = 2000, message = "Năm bắt đầu phải từ năm 2000 trở đi")
    @Max(value = 2100, message = "Năm bắt đầu không hợp lệ")
    private Integer startYear;

    @NotNull(message = "Năm kết thúc là bắt buộc")
    @Min(value = 2000, message = "Năm kết thúc phải từ năm 2000 trở đi")
    @Max(value = 2100, message = "Năm kết thúc không hợp lệ")
    private Integer endYear;

    private CohortStatus status;
}
