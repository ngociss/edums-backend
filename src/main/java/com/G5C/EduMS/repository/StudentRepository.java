package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // Kiểm tra ràng buộc trước khi xóa Specialization
    boolean existsBySpecializationIdAndDeletedFalse(Integer specializationId);
}

