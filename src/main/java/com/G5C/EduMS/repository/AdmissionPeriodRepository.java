package com.G5C.EduMS.repository;

import com.G5C.EduMS.common.enums.AdmissionPeriodStatus;
import com.G5C.EduMS.model.AdmissionPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdmissionPeriodRepository extends JpaRepository<AdmissionPeriod, Integer> {

    @Query("SELECT p FROM AdmissionPeriod p " +
            "WHERE p.status = :status " +
            "AND CURRENT_TIMESTAMP BETWEEN p.startTime AND p.endTime " +
            "AND p.deleted = false")
    List<AdmissionPeriod> findActivePeriods(@Param("status") AdmissionPeriodStatus status);

    boolean existsByIdAndStatusAndEndTimeAfterAndDeletedFalse(Integer id, AdmissionPeriodStatus status, LocalDateTime now);

    // 1. Kiểm tra trùng tên
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AdmissionPeriod a " +
            "WHERE a.deleted = false AND a.periodName = :name " +
            "AND (:currentId IS NULL OR a.id != :currentId)")
    boolean existsByNameAndNotId(@Param("name") String name, @Param("currentId") Integer currentId);

    // 2. Kiểm tra trùng thời gian (Overlap)
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AdmissionPeriod a " +
            "WHERE a.deleted = false " +
            "AND a.startTime < :newEndTime " +
            "AND a.endTime > :newStartTime " +
            "AND (:currentId IS NULL OR a.id != :currentId)")
    boolean existsOverlappingPeriod(@Param("newStartTime") LocalDateTime newStartTime,
                                    @Param("newEndTime") LocalDateTime newEndTime,
                                    @Param("currentId") Integer currentId);

    boolean existsByPeriodNameAndDeletedFalse(String periodName);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AdmissionPeriod a " +
            "WHERE a.deleted = false AND a.startTime < :newEndTime AND a.endTime > :newStartTime")
    boolean existsOverlappingPeriod(@Param("newStartTime") LocalDateTime newStartTime,
                                    @Param("newEndTime") LocalDateTime newEndTime);

    List<AdmissionPeriod> findAllByDeletedFalse();

    Optional<AdmissionPeriod> findByIdAndDeletedFalse(Integer id);

    Page<AdmissionPeriod> findAllByDeletedFalse(Pageable pageable);
}