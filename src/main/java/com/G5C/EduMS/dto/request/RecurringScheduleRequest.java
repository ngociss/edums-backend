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

    @NotNull(message = "Section ID là bắt buộc")
    private Integer sectionId;

    @NotNull(message = "Classroom ID là bắt buộc")
    private Integer classroomId;

    @NotNull(message = "Thứ trong tuần là bắt buộc")
    @Min(value = 1, message = "Thứ trong tuần phải từ 1 (Thứ Hai) đến 7 (Chủ Nhật)")
    @Max(value = 7, message = "Thứ trong tuần phải từ 1 (Thứ Hai) đến 7 (Chủ Nhật)")
    private Integer dayOfWeek;

    @NotNull(message = "Tiết bắt đầu là bắt buộc")
    @Min(value = 1, message = "Tiết học phải từ 1 đến 4")
    @Max(value = 4, message = "Tiết học phải từ 1 đến 4")
    private Integer startPeriod;

    @NotNull(message = "Tiết kết thúc là bắt buộc")
    @Min(value = 1, message = "Tiết học phải từ 1 đến 4")
    @Max(value = 4, message = "Tiết học phải từ 1 đến 4")
    private Integer endPeriod;
}

