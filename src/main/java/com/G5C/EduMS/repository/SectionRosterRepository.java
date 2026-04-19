package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.SectionRoster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRosterRepository extends JpaRepository<SectionRoster, Integer> {

    Optional<SectionRoster> findByIdAndDeletedFalse(Integer id);

    Optional<SectionRoster> findBySection_IdAndStudent_IdAndDeletedFalse(Integer sectionId, Integer studentId);

    Optional<SectionRoster> findByCourseRegistration_IdAndDeletedFalse(Integer courseRegistrationId);

    @Query("""
        SELECT sr
        FROM SectionRoster sr
        JOIN FETCH sr.student s
        JOIN FETCH sr.courseRegistration cr
        JOIN FETCH cr.section sec
        LEFT JOIN FETCH sr.sourceRegistrationPeriod rp
        WHERE sr.section.id = :sectionId
          AND sr.deleted = false
          AND s.deleted = false
          AND cr.deleted = false
          AND sec.deleted = false
        ORDER BY s.studentCode ASC
    """)
    List<SectionRoster> findAllBySectionIdAndDeletedFalse(@Param("sectionId") Integer sectionId);
}
