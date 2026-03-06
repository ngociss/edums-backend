package com.G5C.EduMS.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_name", length = 255)
    private String roleName;

    @Column(name = "status")
    @Builder.Default
    private Integer status = 1;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<RolePermission> permissions;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<Account> accounts;
}
