package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true) // Kế thừa các thuộc tính phân trang từ BaseFilterRequest
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationFilterRequest extends BaseFilterRequest {

    private Integer periodId; // Lọc theo Đợt tuyển sinh (Rất hay dùng)

    private Integer majorId; // Lọc theo Ngành học (Để xem ngành nào đang hot)

    private ApplicationStatus status; // Lọc theo Trạng thái (PENDING, APPROVED, REJECTED)

    private String keyword; // Tìm kiếm tự do theo Tên hoặc Số CCCD
}