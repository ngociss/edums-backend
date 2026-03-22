package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.BenchmarkScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BenchmarkScoreRepository extends JpaRepository<BenchmarkScore, Integer> {

    // Kiểm tra ràng buộc trước khi xóa Major
    boolean existsByMajorId(Integer majorId);

    // Tìm điểm chuẩn chính xác của một Ngành + Khối trong một Đợt cụ thể
    Optional<BenchmarkScore> findByMajorIdAndAdmissionBlockIdAndAdmissionPeriodIdAndDeletedFalse(
            Integer majorId, Integer admissionBlockId, Integer admissionPeriodId);

    List<BenchmarkScore> findAllByAdmissionPeriodIdAndDeletedFalse(Integer integers);
}

