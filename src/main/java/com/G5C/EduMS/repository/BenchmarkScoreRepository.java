package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.AdmissionBlock;
import com.G5C.EduMS.model.BenchmarkScore;
import com.G5C.EduMS.model.Major;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    boolean existsByAdmissionBlockIdAndDeletedFalse(Integer id);

    Optional<BenchmarkScore> findByIdAndDeletedFalse(Integer id);

    Page<BenchmarkScore> findAllByDeletedFalse(Pageable pageable);

    @Query("SELECT DISTINCT b.major FROM BenchmarkScore b " +
            "WHERE b.admissionPeriod.id = :periodId " +
            "AND b.deleted = false " +
            "AND b.major.deleted = false")
    List<Major> findDistinctMajorsByPeriodId(@Param("periodId") Integer periodId);

    @Query("SELECT DISTINCT b.admissionBlock FROM BenchmarkScore b " +
            "WHERE b.admissionPeriod.id = :periodId " +
            "AND b.major.id = :majorId " +
            "AND b.deleted = false " +
            "AND b.admissionBlock.deleted = false")
    List<AdmissionBlock> findDistinctBlocksByPeriodIdAndMajorId(
            @Param("periodId") Integer periodId,
            @Param("majorId") Integer majorId);

}

