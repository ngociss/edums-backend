package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.reponse.AccountResponse;
import com.G5C.EduMS.model.Account;
import com.G5C.EduMS.model.Role;
import com.G5C.EduMS.model.RolePermission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    // 1. Chuyển từ Entity sang Response DTO
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.roleName", target = "roleName")
    @Mapping(source = "role", target = "permissions", qualifiedByName = "mapRolePermissions")
    AccountResponse toResponse(Account account);

    // 2. Chuyển danh sách Entity sang danh sách Response DTO (Dùng cho phân trang)
    // MapStruct sẽ tự động gọi hàm toResponse() cho từng phần tử trong List
    List<AccountResponse> toResponseList(List<Account> accounts);

    // --- Hàm custom để lấy danh sách mã quyền từ Role ---
    @Named("mapRolePermissions")
    default List<String> mapRolePermissions(Role role) {
        if (role == null || role.getPermissions() == null) {
            return Collections.emptyList();
        }
        return role.getPermissions().stream()
                .filter(p -> !p.isDeleted()) // Bỏ qua các quyền đã bị xóa mềm
                .map(RolePermission::getFunctionCode)
                .collect(Collectors.toList());
    }
}
