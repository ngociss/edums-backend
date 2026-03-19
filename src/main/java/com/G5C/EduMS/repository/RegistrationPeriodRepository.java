package com.G5C.EduMS.repository;



import com.G5C.EduMS.model.RegistrationPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RegistrationPeriodRepository extends JpaRepository<RegistrationPeriod, Integer> {

    @Query("""
        SELECT rp
        FROM RegistrationPeriod rp
        WHERE rp.semester.id = :semesterId
          AND rp.deleted = false
          AND rp.status = com.G5C.EduMS.common.enums.RegistrationPeriodStatus.OPEN
          AND rp.startTime <= :now
          AND rp.endTime >= :now
    """)
    Optional<RegistrationPeriod> findOpenPeriodBySemesterId(
            Integer semesterId,
            LocalDateTime now
    );

    @Query("""
    SELECT COUNT(rp) > 0
    FROM RegistrationPeriod rp
    WHERE rp.semester.id = :semesterId
      AND rp.deleted = false
      AND rp.startTime <= :endTime
      AND rp.endTime >= :startTime
""")
    boolean existsOverlappingPeriod(
            Integer semesterId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}