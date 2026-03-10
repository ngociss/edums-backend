package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.AdministrativeClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrativeClassRepository extends JpaRepository<AdministrativeClass, Integer> {

    // Kiểm tra ràng buộc trước khi xóa Major
    boolean existsByMajorIdAndDeletedFalse(Integer majorId);
}

