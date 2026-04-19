package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.SemesterStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class SemesterRequest {

    @NotNull(message = "Số học kỳ là bắt buộc")
    @Min(value = 1, message = "Số học kỳ phải từ 1 trở lên")
    @Max(value = 12, message = "Số học kỳ không được lớn hơn 12")
    private Integer semesterNumber;

    @NotBlank(message = "Năm học là bắt buộc")
    @Size(max = 50, message = "Năm học không được vượt quá 50 ký tự")
    private String academicYear;

    @NotNull(message = "Ngày bắt đầu là bắt buộc")
    private LocalDate startDate;

    @NotNull(message = "Tổng số tuần là bắt buộc")
    @Min(value = 1, message = "Tổng số tuần phải lớn hơn 0")
    private Integer totalWeeks;

    private SemesterStatus status;

}
