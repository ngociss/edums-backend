package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleNameAndDeletedFalse(String roleName);

}