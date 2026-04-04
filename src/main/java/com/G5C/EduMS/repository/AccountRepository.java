package com.G5C.EduMS.repository;

import com.G5C.EduMS.common.enums.AccountStatus;
import com.G5C.EduMS.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsByUsername(String username);

    boolean existsByUsernameAndDeletedFalse(String username);

    @Query("SELECT a FROM Account a WHERE " +
            "(:keyword IS NULL OR LOWER(a.username) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:roleId IS NULL OR a.role.id = :roleId) AND " +
            "(:status IS NULL OR a.status = :status) AND " +
            "(:prefix IS NULL OR LOWER(a.username) LIKE LOWER(CONCAT(:prefix, '%')))")
    Page<Account> searchAccounts(@Param("keyword") String keyword,
                                 @Param("roleId") Integer roleId,
                                 @Param("status") AccountStatus status,
                                 @Param("prefix") String prefix,
                                 Pageable pageable);

    boolean existsByRoleIdAndDeletedFalse(Integer roleId);

    @EntityGraph(attributePaths = {"role"})
    Optional<Account> findByUsernameAndDeletedFalse(String username);

    List<Account> findAllByUsernameIn(Set<String> usernames);

    Optional<Account> findByIdAndDeletedFalse(Integer integer);
}
