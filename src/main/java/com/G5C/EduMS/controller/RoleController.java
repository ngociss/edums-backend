package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.reponse.ResponseData;
import com.G5C.EduMS.dto.reponse.RoleResponse;
import com.G5C.EduMS.dto.request.RoleRequest;
import com.G5C.EduMS.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    // 1. Lấy danh sách mã quyền
    @GetMapping("/permissions")
    public ResponseEntity<ResponseData<List<String>>> getAllPermissions() {
        List<String> data = roleService.getAllSystemPermissions();
        ResponseData<List<String>> response = ResponseData.success("Lấy danh sách mã quyền thành công", data, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    // 2. Lấy danh sách tất cả Role
    @GetMapping
    public ResponseEntity<ResponseData<List<RoleResponse>>> getAllRoles() {
        List<RoleResponse> data = roleService.getAllRoles();
        ResponseData<List<RoleResponse>> response = ResponseData.success("Lấy danh sách vai trò thành công", data, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    // 3. Lấy chi tiết Role theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<RoleResponse>> getRoleById(@PathVariable Integer id) {
        RoleResponse data = roleService.getRoleById(id);
        ResponseData<RoleResponse> response = ResponseData.success("Lấy chi tiết vai trò thành công", data, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    // 4. Tạo Role mới
    @PostMapping
    public ResponseEntity<ResponseData<RoleResponse>> createRole(@Valid @RequestBody RoleRequest request) {
        RoleResponse data = roleService.createRole(request);
        ResponseData<RoleResponse> response = ResponseData.success("Tạo vai trò thành công", data, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 5. Cập nhật Role
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<RoleResponse>> updateRole(@PathVariable Integer id, @Valid @RequestBody RoleRequest request) {
        RoleResponse data = roleService.updateRole(id, request);
        ResponseData<RoleResponse> response = ResponseData.success("Cập nhật vai trò thành công", data, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    // 6. Xóa mềm Role
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Void>> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        // HttpStatus 204 (No Content) thường không có body, nhưng nếu bạn muốn trả JSON thì dùng 200 (OK)
        ResponseData<Void> response = ResponseData.success("Xóa vai trò thành công", null, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}
