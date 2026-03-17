package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.reponse.RoleResponse;
import com.G5C.EduMS.dto.request.RoleRequest;
import com.G5C.EduMS.mapper.RoleMapper;
import com.G5C.EduMS.model.Role;
import com.G5C.EduMS.model.RolePermission;
import com.G5C.EduMS.repository.AccountRepository;
import com.G5C.EduMS.repository.RolePermissionRepository;
import com.G5C.EduMS.repository.RoleRepository;
import com.G5C.EduMS.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final AccountRepository accountRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<String> getAllSystemPermissions() {
        // Do không có bảng Danh mục Quyền độc lập, ta định nghĩa danh sách các mã quyền
        // hợp lệ tại đây để Frontend có thể lấy về và render danh sách checkbox.
        return Arrays.asList(
                // Quản lý Tài khoản & Phân quyền
                "VIEW_ACCOUNTS", "CREATE_ACCOUNT", "UPDATE_ACCOUNT", "DELETE_ACCOUNT",
                "VIEW_ROLES", "CREATE_ROLE", "UPDATE_ROLE", "DELETE_ROLE",

                // Quản lý Tuyển sinh
                "VIEW_ADMISSIONS", "MANAGE_ADMISSIONS", "APPROVE_ADMISSION",

                // Quản lý Đào tạo (Ví dụ)
                "VIEW_MAJORS", "MANAGE_MAJORS",
                "VIEW_BENCHMARKS", "MANAGE_BENCHMARKS"
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getAllRoles() {
        // Chỉ lấy những Role chưa bị xóa mềm
        List<Role> roles = roleRepository.findAll().stream()
                .filter(role -> !role.isDeleted())
                .collect(Collectors.toList());
        return roleMapper.toResponseList(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getRoleById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Vai trò với ID: " + id));

        if (role.isDeleted()) {
            throw new RuntimeException("Vai trò này đã bị xóa!");
        }
        return roleMapper.toResponse(role);
    }
    @Override
    @Transactional
    public RoleResponse createRole(RoleRequest request) {
        // 1. Dùng Mapper để tạo Entity Role cơ bản (đã ignore id và permissions)
        Role newRole = roleMapper.toEntity(request);

        // 2. Lưu Role vào Database TRƯỚC để lấy ID
        Role savedRole = roleRepository.save(newRole);

        // 3. Xử lý danh sách quyền (functionCodes)
        if (request.getFunctionCodes() != null && !request.getFunctionCodes().isEmpty()) {
            List<RolePermission> permissions = new ArrayList<>();
            for (String code : request.getFunctionCodes()) {
                permissions.add(RolePermission.builder()
                        .role(savedRole) // Gắn Role đã có ID vào đây
                        .functionCode(code)
                        .build());
            }
            // Lưu toàn bộ quyền xuống DB
            rolePermissionRepository.saveAll(permissions);

            // Cập nhật lại list permissions cho object savedRole để trả về Frontend
            savedRole.setPermissions(permissions);
        }

        return roleMapper.toResponse(savedRole);
    }

    @Override
    @Transactional
    public RoleResponse updateRole(Integer id, RoleRequest request) {
        // 1. Tìm Role cũ trong DB
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Vai trò để cập nhật!"));

        // 2. Cập nhật tên Vai trò
        existingRole.setRoleName(request.getRoleName());
        Role savedRole = roleRepository.save(existingRole);

        // 3. XÓA SẠCH các quyền cũ của Role này trong DB
        rolePermissionRepository.deleteAllByRoleId(id);

        // 4. THÊM LẠI các quyền mới từ Request
        if (request.getFunctionCodes() != null && !request.getFunctionCodes().isEmpty()) {
            List<RolePermission> newPermissions = new ArrayList<>();
            for (String code : request.getFunctionCodes()) {
                newPermissions.add(RolePermission.builder()
                        .role(savedRole)
                        .functionCode(code)
                        .build());
            }
            rolePermissionRepository.saveAll(newPermissions);
            savedRole.setPermissions(newPermissions);
        } else {
            // Nếu Frontend gửi lên mảng rỗng, nghĩa là xóa hết quyền
            savedRole.setPermissions(new ArrayList<>());
        }

        return roleMapper.toResponse(savedRole);
    }

    @Override
    @Transactional
    public void deleteRole(Integer id) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Vai trò để xóa!"));

        // 🚨 CHỐT CHẶN AN TOÀN: Kiểm tra xem có Account nào đang dùng Role này không
        if (accountRepository.existsByRoleId(id)) {
            throw new RuntimeException("Không thể xóa Vai trò này vì đang có Tài khoản sử dụng!");
        }

        // Thực hiện xóa mềm
        existingRole.setDeleted(true);
        roleRepository.save(existingRole);
    }
}
