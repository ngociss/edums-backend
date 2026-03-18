package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Guardian;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GuardianRepository extends JpaRepository<Guardian, Integer> {
    // Lấy tất cả phụ huynh chưa bị xóa
    Page<Guardian> findAllByDeletedFalse(Pageable pageable);

    // Tìm phụ huynh theo ID và chưa bị xóa
    Optional<Guardian> findByIdAndDeletedFalse(Integer id);

    // Kiểm tra số điện thoại có tồn tại không (dùng khi Create/Update)
    boolean existsByPhoneAndDeletedFalse(String phone);

    // Tìm kiếm theo tên hoặc số điện thoại (Sử dụng câu lệnh JPQL)
    @Query("SELECT g FROM Guardian g WHERE g.deleted = false AND " +
            "(LOWER(g.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "g.phone LIKE CONCAT('%', :keyword, '%'))")
    Page<Guardian> searchGuardians(@Param("keyword") String keyword, Pageable pageable);
}
