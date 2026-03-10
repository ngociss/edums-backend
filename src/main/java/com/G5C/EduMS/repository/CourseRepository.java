package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    // Kiểm tra ràng buộc trước khi xóa Faculty
    boolean existsByFacultyIdAndDeletedFalse(Integer facultyId);
}

