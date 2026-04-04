package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.response.RoleResponse;
import com.G5C.EduMS.dto.request.RoleRequest;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
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
        List<Role> roles = roleRepository.findAllByDeletedFalse();
        return roleMapper.toResponseList(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getRoleById(Integer id) {
        Role role = roleRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Vai trò với ID: " + id));

        return roleMapper.toResponse(role);
    }
    @Override
    @Transactional
    public RoleResponse createRole(RoleRequest request) {
        if (request.getFunctionCodes() == null || request.getFunctionCodes().isEmpty()) {
            throw new InvalidDataException("Danh sách quyền không được để trống");
        }
        if (roleRepository.existsByRoleNameAndDeletedFalse(request.getRoleName())) {
            throw new ExistingResourcesException("Tên Vai trò này đã tồn tại trong hệ thống!");
        }
        Role newRole = roleMapper.toEntity(request);
        Role savedRole = roleRepository.save(newRole);

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
        if (request.getFunctionCodes() == null || request.getFunctionCodes().isEmpty()) {
            throw new InvalidDataException("Danh sách quyền không được để trống");
        }
        // 1. Tìm Role cũ trong DB
        Role existingRole = roleRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Vai trò để cập nhật!"));

        if (!existingRole.getRoleName().equals(request.getRoleName()) &&
                roleRepository.existsByRoleNameAndDeletedFalse(request.getRoleName())) {
            throw new ExistingResourcesException("Tên Vai trò này đã tồn tại trong hệ thống!");
        }
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
        Role existingRole = roleRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Vai trò để xóa!"));

        if (accountRepository.existsByRoleIdAndDeletedFalse(id)) {
            throw new InvalidDataException("Không thể xóa Vai trò này vì đang có Tài khoản sử dụng!");
        }

        // Thực hiện xóa mềm
        existingRole.setDeleted(true);
        roleRepository.save(existingRole);
    }
}
