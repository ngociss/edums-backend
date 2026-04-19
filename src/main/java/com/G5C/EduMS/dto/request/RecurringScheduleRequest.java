package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecurringScheduleRequest {

    @NotNull(message = "Mã lớp học phần là bắt buộc")
    private Integer sectionId;

    @NotNull(message = "Mã phòng học là bắt buộc")
    private Integer classroomId;

    @NotNull(message = "Thứ trong tuần là bắt buộc")
    @Min(value = 1, message = "Thứ trong tuần phải từ 1 (Thứ Hai) đến 7 (Chủ Nhật)")
    @Max(value = 7, message = "Thứ trong tuần phải từ 1 (Thứ Hai) đến 7 (Chủ Nhật)")
    private Integer dayOfWeek;

    @NotNull(message = "Tiết bắt đầu là bắt buộc")
    @Min(value = 1, message = "Tiết học phải từ 1 đến 10")
    @Max(value = 10, message = "Tiết học phải từ 1 đến 10")
    private Integer startPeriod;

    @NotNull(message = "Tiết kết thúc là bắt buộc")
    @Min(value = 1, message = "Tiết học phải từ 1 đến 10")
    @Max(value = 10, message = "Tiết học phải từ 1 đến 10")
    private Integer endPeriod;

    @Min(value = 1, message = "Tuần bắt đầu phải lớn hơn hoặc bằng 1")
    private Integer startWeek;

    @Min(value = 1, message = "Tuần kết thúc phải lớn hơn hoặc bằng 1")
    private Integer endWeek;
}

