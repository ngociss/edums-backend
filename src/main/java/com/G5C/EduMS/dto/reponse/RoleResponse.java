package com.G5C.EduMS.dto.reponse;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {

    private Integer id;
    private String roleName;

    // Flattened from the RolePermission list
    private List<String> functionCodes;
}