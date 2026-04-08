package com.G5C.EduMS.repository;



import com.G5C.EduMS.common.enums.RegistrationPeriodStatus;
import com.G5C.EduMS.model.RegistrationPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationPeriodRepository extends JpaRepository<RegistrationPeriod, Integer> {

    List<RegistrationPeriod> findAllByDeletedFalse();

    List<RegistrationPeriod> findAllBySemester_IdAndDeletedFalse(Integer semesterId);

    Optional<RegistrationPeriod> findByIdAndDeletedFalse(Integer id);

    boolean existsBySemester_IdAndDeletedFalse(Integer semesterId);

    boolean existsBySemester_IdAndIdNotAndDeletedFalse(Integer semesterId, Integer id);

    boolean existsBySemester_IdAndStatusAndDeletedFalse(Integer semesterId, RegistrationPeriodStatus status);

    boolean existsBySemester_IdAndIdNotAndStatusAndDeletedFalse(
            Integer semesterId,
            Integer id,
            RegistrationPeriodStatus status
    );

    boolean existsByStatusAndDeletedFalse(com.G5C.EduMS.common.enums.RegistrationPeriodStatus status);

    boolean existsByStatusAndIdNotAndDeletedFalse(
            com.G5C.EduMS.common.enums.RegistrationPeriodStatus status,
            Integer id
    );

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
        SELECT rp
        FROM RegistrationPeriod rp
        WHERE rp.deleted = false
          AND rp.status = com.G5C.EduMS.common.enums.RegistrationPeriodStatus.OPEN
          AND rp.startTime <= :now
          AND rp.endTime >= :now
    """)
    List<RegistrationPeriod> findAllOpenPeriods(LocalDateTime now);

    @Query("""
    SELECT COUNT(rp) > 0
    FROM RegistrationPeriod rp
    WHERE rp.semester.id = :semesterId
      AND rp.deleted = false
      AND rp.status IN (
          com.G5C.EduMS.common.enums.RegistrationPeriodStatus.UPCOMING,
          com.G5C.EduMS.common.enums.RegistrationPeriodStatus.OPEN
      )
      AND rp.startTime <= :endTime
      AND rp.endTime >= :startTime
""")
    boolean existsOverlappingPeriod(
            Integer semesterId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    @Query("""
    SELECT COUNT(rp) > 0
    FROM RegistrationPeriod rp
    WHERE rp.semester.id = :semesterId
      AND rp.deleted = false
      AND (:excludeId IS NULL OR rp.id <> :excludeId)
      AND rp.status IN (
          com.G5C.EduMS.common.enums.RegistrationPeriodStatus.UPCOMING,
          com.G5C.EduMS.common.enums.RegistrationPeriodStatus.OPEN
      )
      AND rp.startTime <= :endTime
      AND rp.endTime >= :startTime
""")
    boolean existsOverlappingPeriodExcludingId(
            Integer semesterId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Integer excludeId
    );
}
