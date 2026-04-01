package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Integer> {

    List<Semester> findAllByDeletedFalse();

    Optional<Semester> findByIdAndDeletedFalse(Integer id);

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

