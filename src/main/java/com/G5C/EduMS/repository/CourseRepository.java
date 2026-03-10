package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findAllByDeletedFalse();

    List<Course> findAllByFacultyIdAndDeletedFalse(Integer facultyId);

    Optional<Course> findByIdAndDeletedFalse(Integer id);

    // Kiểm tra ràng buộc trước khi xóa Faculty
    boolean existsByFacultyIdAndDeletedFalse(Integer facultyId);

    // Duplicate check
    boolean existsByCourseCodeAndDeletedFalse(String courseCode);

    boolean existsByCourseCodeAndIdNotAndDeletedFalse(String courseCode, Integer id);

    boolean existsByCourseNameAndFacultyIdAndDeletedFalse(String courseName, Integer facultyId);

    boolean existsByCourseNameAndFacultyIdAndIdNotAndDeletedFalse(String courseName, Integer facultyId, Integer id);

    // Kiểm tra ràng buộc trước khi xóa Course (prerequisite)
    boolean existsByPrerequisiteCourseIdAndDeletedFalse(Integer prerequisiteCourseId);
}
