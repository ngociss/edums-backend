package com.G5C.EduMS.repository;

import com.G5C.EduMS.common.enums.StudentStatus;
import com.G5C.EduMS.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // Kiểm tra ràng buộc trước khi xóa Specialization
    boolean existsBySpecializationIdAndDeletedFalse(Integer specializationId);

    boolean existsByGuardianIdAndDeletedFalse(Integer id);

    Optional<Student> findByAccount_IdAndDeletedFalse(Integer accountId);

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


    // 1. Dùng để lấy nguyên cả danh sách object Student (Nếu bạn có dùng trong logic)
    Set<Student> findByNationalIdInAndDeletedFalse(Collection<String> nationalIds);

    // 2. Dùng để chỉ lấy ra 1 Set các số CCCD (Tối ưu hiệu suất, dùng cho Unit Test của bạn)
    @Query("SELECT s.nationalId FROM Student s WHERE s.nationalId IN :nationalIds AND s.deleted = false")
    Set<String> findNationalIdsInAndDeletedFalse(@Param("nationalIds") Collection<String> nationalIds);

    // 3. Dùng để chỉ lấy ra 1 Set các Email
    @Query("SELECT s.email FROM Student s WHERE s.email IN :emails AND s.deleted = false")
    Set<String> findEmailsInAndDeletedFalse(@Param("emails") Collection<String> emails);
}

