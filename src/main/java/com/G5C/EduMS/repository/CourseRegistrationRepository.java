package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {

    Optional<CourseRegistration> findByIdAndDeletedFalse(Integer id);

    @Query("SELECT cr FROM CourseRegistration cr WHERE cr.section.id = :sectionId AND cr.deleted = false")
    List<CourseRegistration> findAllBySectionId(@Param("sectionId") Integer sectionId);

    @Query("SELECT cr FROM CourseRegistration cr WHERE cr.student.id = :studentId AND cr.deleted = false")
    List<CourseRegistration> findAllByStudentId(@Param("studentId") Integer studentId);
}

