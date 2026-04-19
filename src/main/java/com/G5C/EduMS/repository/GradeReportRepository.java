package com.G5C.EduMS.repository;

import com.G5C.EduMS.common.enums.GradeStatus;
import com.G5C.EduMS.model.GradeReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeReportRepository extends JpaRepository<GradeReport, Integer> {

    Optional<GradeReport> findByIdAndDeletedFalse(Integer id);

    Optional<GradeReport> findByRegistrationIdAndDeletedFalse(Integer registrationId);

    @Query("SELECT gr FROM GradeReport gr WHERE gr.registration.student.id = :studentId AND gr.deleted = false")
    List<GradeReport> findAllByStudentId(@Param("studentId") Integer studentId);

    @Query("""
        SELECT gr
        FROM GradeReport gr
        WHERE gr.id = :id
          AND gr.registration.student.id = :studentId
          AND gr.deleted = false
    """)
    Optional<GradeReport> findByIdAndStudentId(@Param("id") Integer id, @Param("studentId") Integer studentId);

    @Query("SELECT gr FROM GradeReport gr WHERE gr.registration.section.id = :sectionId AND gr.deleted = false")
    List<GradeReport> findAllBySectionId(@Param("sectionId") Integer sectionId);

    @Query("""
        SELECT DISTINCT gr
        FROM GradeReport gr
        JOIN FETCH gr.registration reg
        JOIN FETCH reg.student stu
        JOIN FETCH reg.section sec
        JOIN FETCH sec.course c
        LEFT JOIN FETCH gr.gradeDetails gd
        LEFT JOIN FETCH gd.component comp
        WHERE sec.id = :sectionId
          AND gr.deleted = false
          AND reg.deleted = false
          AND stu.deleted = false
          AND sec.deleted = false
          AND c.deleted = false
    """)
    List<GradeReport> findAllBySectionIdWithDetails(@Param("sectionId") Integer sectionId);

    boolean existsByRegistrationIdAndDeletedFalse(Integer registrationId);

    @Query("""
        SELECT COUNT(gr) > 0
        FROM GradeReport gr
        WHERE gr.registration.student.id = :studentId
          AND gr.registration.section.course.id = :courseId
          AND gr.deleted = false
          AND gr.status IN :statuses
          AND gr.finalScore IS NOT NULL
          AND gr.finalScore >= :minimumScore
    """)
    boolean existsPassedCourseByStudentIdAndCourseId(
            @Param("studentId") Integer studentId,
            @Param("courseId") Integer courseId,
            @Param("statuses") List<GradeStatus> statuses,
            @Param("minimumScore") float minimumScore
    );
}
