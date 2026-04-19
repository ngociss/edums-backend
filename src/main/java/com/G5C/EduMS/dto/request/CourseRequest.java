package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.CourseStatus;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequest {

    @NotBlank(message = "Mã môn học là bắt buộc")
    @Size(max = 20, message = "Mã môn học không được vượt quá 20 ký tự")
    private String courseCode;

    @NotBlank(message = "Tên môn học là bắt buộc")
    @Size(max = 255, message = "Tên môn học không được vượt quá 255 ký tự")
    private String courseName;

    @NotNull(message = "Số tín chỉ là bắt buộc")
    @Min(value = 1, message = "Số tín chỉ phải lớn hơn hoặc bằng 1")
    @Max(value = 10, message = "Số tín chỉ không được vượt quá 10")
    private Integer credits;

    @NotNull(message = "Mã khoa là bắt buộc")
    private Integer facultyId;

    // null = không có môn tiên quyết
    private Integer prerequisiteCourseId;

    private CourseStatus status = CourseStatus.ACTIVE;
}

