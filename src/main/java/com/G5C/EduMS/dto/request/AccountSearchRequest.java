package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.AccountStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountSearchRequest {

    // Tìm kiếm tương đối (LIKE) theo trường username
    private String keyword;

    // Lọc theo id của Role (nếu truyền lên null thì lấy tất cả Role)
    private Integer roleId;

    // Lọc theo trạng thái tài khoản (ACTIVE, LOCKED)
    private AccountStatus status;

    // Lọc tiền tố username (vd: "sv", "gv")
    private String prefix;

    // --- Thông tin phân trang ---
    // Đặt giá trị mặc định để tránh lỗi NullPointerException nếu Frontend không truyền
    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    // --- Thông tin sắp xếp (Tùy chọn) ---
    // Ví dụ gửi lên: "createdAt,desc" (Mới nhất lên đầu) hoặc "username,asc" (Theo A-Z)
    private String sortBy;
}
