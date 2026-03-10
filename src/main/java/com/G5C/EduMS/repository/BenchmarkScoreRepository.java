package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.BenchmarkScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenchmarkScoreRepository extends JpaRepository<BenchmarkScore, Integer> {

    // Kiểm tra ràng buộc trước khi xóa Major
    boolean existsByMajorId(Integer majorId);
}

