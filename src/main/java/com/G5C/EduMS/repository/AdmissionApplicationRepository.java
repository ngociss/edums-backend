package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.AdmissionApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionApplicationRepository extends JpaRepository<AdmissionApplication, Integer> {

    // Kiểm tra ràng buộc trước khi xóa Major
    boolean existsByMajorIdAndDeletedFalse(Integer majorId);
}

