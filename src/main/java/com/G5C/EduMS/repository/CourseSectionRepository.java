package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.CourseSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseSectionRepository extends JpaRepository<CourseSection, Integer> {

    List<CourseSection> findAllByDeletedFalse();

    List<CourseSection> findAllByCourseIdAndDeletedFalse(Integer courseId);

    List<CourseSection> findAllBySemesterIdAndDeletedFalse(Integer semesterId);

    Optional<CourseSection> findByIdAndDeletedFalse(Integer id);

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

