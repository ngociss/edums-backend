package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Lecturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
    // Lấy tất cả giảng viên đang hoạt động
    Page<Lecturer> findAllByDeletedFalse(Pageable pageable);

    // Tìm chi tiết 1 giảng viên đang hoạt động
    Optional<Lecturer> findByIdAndDeletedFalse(Integer id);

    // Kiểm tra trùng lặp Email
    boolean existsByEmailAndDeletedFalse(String email);

    // Tìm kiếm theo tên hoặc email (không phân biệt hoa/thường)
    @Query("SELECT l FROM Lecturer l WHERE l.deleted = false AND " +
            "(LOWER(l.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Lecturer> searchLecturers(@Param("keyword") String keyword, Pageable pageable);

    Optional<Lecturer> findByAccount_UsernameAndDeletedFalse(String username);
}

