package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.AdministrativeClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdministrativeClassRepository extends JpaRepository<AdministrativeClass, Integer> {

    List<AdministrativeClass> findAllByDeletedFalse();

    Optional<AdministrativeClass> findByIdAndDeletedFalse(Integer id);

    boolean existsByClassNameAndDeletedFalse(String className);

    boolean existsByClassNameAndDeletedFalseAndIdNot(String className, Integer id);

    @Query("SELECT COUNT(ac) > 0 FROM AdministrativeClass ac WHERE ac.headLecturer.id = :lecturerId AND ac.deleted = false")
    boolean existsByHeadLecturerIdAndDeletedFalse(@Param("lecturerId") Integer lecturerId);

    @Query("SELECT COUNT(ac) > 0 FROM AdministrativeClass ac WHERE ac.cohort.id = :cohortId AND ac.deleted = false")
    boolean existsByCohortIdAndDeletedFalse(@Param("cohortId") Integer cohortId);

    @Query("SELECT COUNT(ac) > 0 FROM AdministrativeClass ac WHERE ac.major.id = :majorId AND ac.deleted = false")

    boolean existsByMajorIdAndDeletedFalse(Integer majorId);
}
