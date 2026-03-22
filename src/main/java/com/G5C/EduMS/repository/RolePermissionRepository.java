package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    void deleteAllByRoleId(Integer roleId);
}
