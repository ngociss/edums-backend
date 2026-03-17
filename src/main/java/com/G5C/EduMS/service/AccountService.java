package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.reponse.AccountResponse;
import com.G5C.EduMS.dto.reponse.PageResponse;
import com.G5C.EduMS.dto.request.*;

public interface AccountService {

    /**
     * Tương ứng API: GET /api/v1/accounts
     * Lấy danh sách tài khoản (Có hỗ trợ tìm kiếm theo từ khóa, lọc theo Role/Status và phân trang).
     * * @param searchRequest chứa keyword, roleId, status, page, size
     * @return PageResponse chứa danh sách AccountResponse và thông tin phân trang
     */
    PageResponse<AccountResponse> getAccounts(AccountSearchRequest searchRequest);

    /**
     * Tương ứng API: GET /api/v1/accounts/{id}
     * Lấy thông tin chi tiết của 1 tài khoản cụ thể.
     * * @param id của tài khoản cần tìm
     * @return AccountResponse chứa thông tin tài khoản và quyền hạn
     */
    AccountResponse getAccountById(Integer id);

    /**
     * Tương ứng API: POST /api/v1/accounts
     * Tạo tài khoản mới (Thường dùng cho Admin tạo tài khoản nhân viên/giảng viên).
     * Mật khẩu sẽ được mã hóa trước khi lưu.
     * * @param request chứa thông tin tạo tài khoản (username, password, roleId)
     * @return AccountResponse thông tin tài khoản vừa tạo (không bao gồm password)
     */
    AccountResponse createAccount(AccountCreateRequest request);

    /**
     * Tương ứng API: PUT /api/v1/accounts/{id}
     * Cập nhật thông tin cơ bản của tài khoản (Ví dụ: Đổi Role, cập nhật Avatar).
     * * @param id của tài khoản cần cập nhật
     * @param request chứa thông tin cần cập nhật
     * @return AccountResponse thông tin tài khoản sau khi cập nhật
     */
    AccountResponse updateAccount(Integer id, AccountUpdateRequest request);

    /**
     * Tương ứng API: PATCH /api/v1/accounts/{id}/status
     * Cập nhật nhanh trạng thái của tài khoản (Khóa / Mở khóa).
     * * @param id của tài khoản
     * @param request chứa trạng thái mới (ACTIVE, LOCKED)
     */
    void updateAccountStatus(Integer id, AccountStatusUpdateRequest request);

    /**
     * Tương ứng API: PATCH /api/v1/accounts/{id}/reset-password
     * Admin ép tạo lại mật khẩu mới cho user (Khi user báo quên mật khẩu).
     * * @param id của tài khoản
     * @param request chứa mật khẩu mới (sẽ được mã hóa trước khi lưu)
     */
    void resetPassword(Integer id, ResetPasswordRequest request);
}
