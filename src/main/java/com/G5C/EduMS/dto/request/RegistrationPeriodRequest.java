package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.RegistrationPeriodStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationPeriodRequest {

    @NotNull(message = "Mã học kỳ là bắt buộc")
    private Integer semesterId;

    @NotBlank(message = "Tên đợt đăng ký là bắt buộc")
    @Size(max = 100, message = "Tên đợt đăng ký không được vượt quá 100 ký tự")
    private String name;

    @NotNull(message = "Thời gian bắt đầu là bắt buộc")
    private LocalDateTime startTime;

    @NotNull(message = "Thời gian kết thúc là bắt buộc")
    private LocalDateTime endTime;

    @NotNull(message = "Trạng thái là bắt buộc")
    private RegistrationPeriodStatus status;

}
