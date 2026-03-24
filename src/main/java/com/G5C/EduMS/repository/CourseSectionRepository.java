package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.CourseSection;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseSectionRepository extends JpaRepository<CourseSection, Integer> {

    List<CourseSection> findAllByDeletedFalse();

    List<CourseSection> findAllByCourseIdAndDeletedFalse(Integer courseId);

    List<CourseSection> findAllBySemesterIdAndDeletedFalse(Integer semesterId);

    Optional<CourseSection> findByIdAndDeletedFalse(Integer id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT cs FROM CourseSection cs WHERE cs.id = :id AND cs.deleted = false")
    Optional<CourseSection> findByIdAndDeletedFalseForUpdate(@Param("id") Integer id);

    // Duplicate check: section_code unique trong 1 semester
    boolean existsBySectionCodeAndSemesterIdAndDeletedFalse(String sectionCode, Integer semesterId);

    boolean existsBySectionCodeAndCourseIdAndSemesterIdAndIdNotAndDeletedFalse(
            String sectionCode,
            Integer courseId,
            Integer semesterId,
            Integer id
    );
    // Ràng buộc xóa
    boolean existsByCourseIdAndDeletedFalse(Integer courseId);

    boolean existsBySemesterIdAndDeletedFalse(Integer semesterId);


    boolean existsByLecturerIdAndDeletedFalse(Integer lecturerId);
}

