package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.ApplicationStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkReviewRequest {

    @NotEmpty(message = "Danh sách ID hồ sơ không được để trống")
    private List<Integer> applicationIds; // Mảng chứa các ID hồ sơ cần duyệt (Ví dụ: [1, 5, 8, 12])

    @NotNull(message = "Trạng thái xét duyệt không được để trống")
    private ApplicationStatus status; // Trạng thái muốn chuyển sang (Ví dụ: APPROVED hoặc REJECTED)

    private String note; // Ghi chú chung (Nếu duyệt đậu thì có thể để trống, nếu đánh rớt hàng loạt thì ghi lý do)
}