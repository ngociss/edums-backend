package com.G5C.EduMS.repository;

import com.G5C.EduMS.common.enums.SemesterStatus;
import com.G5C.EduMS.model.RegistrationPeriod;
import com.G5C.EduMS.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Integer> {

    List<Semester> findAllByDeletedFalse();

    Optional<Semester> findByIdAndDeletedFalse(Integer id);


    @Query("""
    SELECT s
    FROM Semester s
    WHERE s.id = :semesterId
      AND s.deleted = false
      AND s.status IN :statuses
""")
    Optional<Semester> findByIdAndStatusIn(
            Integer semesterId,
            Collection<SemesterStatus> statuses
    );

    boolean existsBySemesterNumberAndAcademicYearAndDeletedFalse(
            Integer semesterNumber,
            String academicYear
    );

    boolean existsBySemesterNumberAndAcademicYearAndIdNotAndDeletedFalse(
            Integer semesterNumber,
            String academicYear,
            Integer id
    );
}

