package com.G5C.EduMS.dto.request;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequest {

    @NotBlank(message = "Tên vai trò không được để trống")
    private String roleName;

    // Instead of sending complex RolePermission objects, the frontend
    // just sends a list of string codes (e.g., ["ADD_USER", "VIEW_GRADE"])
    private List<String> functionCodes;
}