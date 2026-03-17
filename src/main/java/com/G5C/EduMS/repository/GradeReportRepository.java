package com.G5C.EduMS.repository;

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

    @Query("SELECT gr FROM GradeReport gr WHERE gr.registration.section.id = :sectionId AND gr.deleted = false")
    List<GradeReport> findAllBySectionId(@Param("sectionId") Integer sectionId);

    boolean existsByRegistrationIdAndDeletedFalse(Integer registrationId);
}
