package com.G5C.EduMS.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BenchmarkFilterRequest extends BaseFilterRequest {

    private Integer periodId; // Thường bắt buộc phải chọn đợt trước khi xem điểm

    private Integer majorId; // Lọc xem điểm chuẩn của một ngành cụ thể
}