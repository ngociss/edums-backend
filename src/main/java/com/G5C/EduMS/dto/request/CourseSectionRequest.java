package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.CourseSectionStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseSectionRequest {

    @NotNull(message = "Mã môn học là bắt buộc")
    private Integer courseId;

    private Integer lecturerId; // optional khi DRAFT

    @NotNull(message = "Mã học kỳ là bắt buộc")
    private Integer semesterId;


    @NotNull(message = "Sĩ số tối đa là bắt buộc")
    @Min(value = 1, message = "Sĩ số tối đa phải lớn hơn hoặc bằng 1")
    private Integer maxCapacity;

    private CourseSectionStatus status = CourseSectionStatus.DRAFT;}

