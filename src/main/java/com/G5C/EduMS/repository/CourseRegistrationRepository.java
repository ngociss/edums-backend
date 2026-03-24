package com.G5C.EduMS.repository;

import com.G5C.EduMS.common.enums.RegistrationStatus;
import com.G5C.EduMS.model.CourseRegistration;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {

    Optional<CourseRegistration> findByIdAndDeletedFalse(Integer id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT cr FROM CourseRegistration cr WHERE cr.id = :id AND cr.deleted = false")
    Optional<CourseRegistration> findByIdAndDeletedFalseForUpdate(@Param("id") Integer id);

    @Query("SELECT cr FROM CourseRegistration cr WHERE cr.section.id = :sectionId AND cr.deleted = false")
    List<CourseRegistration> findAllBySectionId(@Param("sectionId") Integer sectionId);

    @Query("SELECT cr FROM CourseRegistration cr WHERE cr.student.id = :studentId AND cr.deleted = false")
    List<CourseRegistration> findAllByStudentId(@Param("studentId") Integer studentId);

    boolean existsByStudent_IdAndSection_IdAndDeletedFalse(Integer studentId, Integer sectionId);

    boolean existsByStudent_IdAndSection_IdAndStatusInAndDeletedFalse(
            Integer studentId,
            Integer sectionId,
            List<RegistrationStatus> statuses
    );

    long countBySection_IdAndStatusAndDeletedFalse(Integer sectionId, RegistrationStatus status);

    List<CourseRegistration> findByStudent_IdAndDeletedFalse(Integer studentId);

    @Query("""
        SELECT cr
        FROM CourseRegistration cr
        WHERE cr.student.id = :studentId
          AND cr.deleted = false
          AND (:semesterId IS NULL OR cr.section.semester.id = :semesterId)
    """)
    List<CourseRegistration> findAllByStudentIdAndSemesterId(
            @Param("studentId") Integer studentId,
            @Param("semesterId") Integer semesterId
    );

    @Query("""
        SELECT cr
        FROM CourseRegistration cr
        WHERE cr.student.id = :studentId
          AND cr.section.semester.id = :semesterId
          AND cr.deleted = false
          AND cr.status IN :statuses
    """)
    List<CourseRegistration> findActiveByStudentIdAndSemesterId(
            @Param("studentId") Integer studentId,
            @Param("semesterId") Integer semesterId,
            @Param("statuses") List<RegistrationStatus> statuses
    );
}


