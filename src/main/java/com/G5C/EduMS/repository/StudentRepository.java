package com.G5C.EduMS.repository;

import com.G5C.EduMS.common.enums.StudentStatus;
import com.G5C.EduMS.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // Kiểm tra ràng buộc trước khi xóa Specialization
    boolean existsBySpecializationIdAndDeletedFalse(Integer specializationId);

    boolean existsByGuardianIdAndDeletedFalse(Integer id);

    Optional<Student> findByAccountIdAndDeletedFalse(Integer accountId);

    // Kiểm tra ràng buộc trước khi xóa AdministrativeClass
    @Query("SELECT COUNT(s) > 0 FROM Student s WHERE s.administrativeClass.id = :classId AND s.deleted = false")
    boolean existsByAdministrativeClassIdAndDeletedFalse(@Param("classId") Integer classId);

    List<Student> findAllByNationalIdInAndDeletedFalse(Set<String> nationalIds);

    Optional<Student> findByIdAndDeletedFalse(Integer id);

    // Kiểm tra trùng lặp
    boolean existsByStudentCodeAndDeletedFalse(String studentCode);
    boolean existsByEmailAndDeletedFalse(String email);
    boolean existsByNationalIdAndDeletedFalse(String nationalId);

    // Lọc đa chiều: Nếu param gửi lên là null thì sẽ bỏ qua điều kiện đó
    @Query("SELECT s FROM Student s WHERE s.deleted = false " +
            "AND (:keyword IS NULL OR LOWER(s.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR s.studentCode LIKE CONCAT('%', :keyword, '%')) " +
            "AND (:classId IS NULL OR s.administrativeClass.id = :classId) " +
            "AND (:majorId IS NULL OR s.major.id = :majorId) " +
            "AND (:status IS NULL OR s.status = :status)")
    Page<Student> searchStudents(@Param("keyword") String keyword,
                                 @Param("classId") Integer classId,
                                 @Param("majorId") Integer majorId,
                                 @Param("status") StudentStatus status,
                                 Pageable pageable);


}

