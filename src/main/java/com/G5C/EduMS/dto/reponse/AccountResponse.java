package com.G5C.EduMS.dto.reponse;

import com.G5C.EduMS.common.enums.AccountStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {

    private Integer id;
    private String username;

    // No password field here!

    private AccountStatus status;
    private String avatarUrl;
    private LocalDateTime createdAt;

    // Flattened Role Data
    private Integer roleId;
    private String roleName;

    // Flattened Permissions from RolePermission
    private List<String> permissions;
}
