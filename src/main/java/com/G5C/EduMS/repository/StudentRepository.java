package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // Kiểm tra ràng buộc trước khi xóa Specialization
    boolean existsBySpecializationIdAndDeletedFalse(Integer specializationId);

    // Kiểm tra ràng buộc trước khi xóa AdministrativeClass
    @Query("SELECT COUNT(s) > 0 FROM Student s WHERE s.administrativeClass.id = :classId AND s.deleted = false")
    boolean existsByAdministrativeClassIdAndDeletedFalse(@Param("classId") Integer classId);
}

