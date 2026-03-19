package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.response.RoleResponse;
import com.G5C.EduMS.dto.request.RoleRequest;
import com.G5C.EduMS.model.Role;
import com.G5C.EduMS.model.RolePermission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(source = "permissions", target = "functionCodes", qualifiedByName = "extractFunctionCodes")
    RoleResponse toResponse(Role role);

    List<RoleResponse> toResponseList(List<Role> roles);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    Role toEntity(RoleRequest request);

    @Named("extractFunctionCodes")
    default List<String> extractFunctionCodes(List<RolePermission> permissions) {
        if (permissions == null) {
            return Collections.emptyList();
        }
        return permissions.stream()
                .filter(p -> !p.isDeleted())
                .map(RolePermission::getFunctionCode)
                .collect(Collectors.toList());
    }
}
