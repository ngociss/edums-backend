package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.reponse.RoleResponse;
import com.G5C.EduMS.dto.request.RoleRequest;

import java.util.List;

public interface RoleService {

    /**
     * Tương ứng API: GET /api/v1/permissions
     * Lấy danh sách tất cả các mã quyền hệ thống hỗ trợ.
     */
    List<String> getAllSystemPermissions();

    /**
     * Tương ứng API: GET /api/v1/roles
     * Lấy danh sách tất cả các Vai trò đang có.
     */
    List<RoleResponse> getAllRoles();

    /**
     * Tương ứng API: GET /api/v1/roles/{id}
     * Lấy thông tin chi tiết của 1 Vai trò (bao gồm danh sách quyền của nó).
     */
    RoleResponse getRoleById(Integer id);

    /**
     * Tương ứng API: POST /api/v1/roles
     * Tạo mới một Vai trò và gán quyền cho nó.
     */
    RoleResponse createRole(RoleRequest request);

    /**
     * Tương ứng API: PUT /api/v1/roles/{id}
     * Cập nhật tên Vai trò hoặc thêm/bớt các quyền.
     */
    RoleResponse updateRole(Integer id, RoleRequest request);

    /**
     * Tương ứng API: DELETE /api/v1/roles/{id}
     * Xóa mềm một Vai trò. Cần ném ngoại lệ nếu đang có Account sử dụng Role này.
     */
    void deleteRole(Integer id);
}
