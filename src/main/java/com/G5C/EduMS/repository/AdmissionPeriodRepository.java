package com.G5C.EduMS.repository;

import com.G5C.EduMS.common.enums.AdmissionPeriodStatus;
import com.G5C.EduMS.model.AdmissionPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdmissionPeriodRepository extends JpaRepository<AdmissionPeriod, Integer> {

    @Query("SELECT p FROM AdmissionPeriod p " +
            "WHERE p.status = :status " +
            "AND CURRENT_TIMESTAMP BETWEEN p.startTime AND p.endTime " +
            "AND p.deleted = false")
    List<AdmissionPeriod> findActivePeriods(@Param("status") AdmissionPeriodStatus status);

    boolean existsByIdAndStatusAndEndTimeAfterAndDeletedFalse(Integer id, AdmissionPeriodStatus status, LocalDateTime now);
}