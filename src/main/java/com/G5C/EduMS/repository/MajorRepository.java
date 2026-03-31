package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MajorRepository extends JpaRepository<Major, Integer> {

    List<Major> findAllByDeletedFalse();

    List<Major> findAllByFacultyIdAndDeletedFalse(Integer facultyId);

    List<Major> findAllByIdInAndDeletedFalse(Set<Integer> majorIds);

    Optional<Major> findByIdAndDeletedFalse(Integer id);

    boolean existsByMajorNameAndFacultyIdAndDeletedFalse(String majorName, Integer facultyId);
    boolean existsByMajorCodeAndFacultyIdAndDeletedFalse(String majorCode, Integer facultyId);

    boolean existsByMajorNameAndFacultyIdAndIdNotAndDeletedFalse(
            String majorName,
            Integer facultyId,
            Integer id
    );

    boolean existsByMajorCodeAndFacultyIdAndIdNotAndDeletedFalse(
            String majorCode,
            Integer facultyId,
            Integer id
    );
    // Kiểm tra ràng buộc trước khi xóa Faculty
    boolean existsByFacultyIdAndDeletedFalse(Integer facultyId);

    @Query("SELECT COUNT(m) FROM Major m WHERE m.id IN :ids AND m.deleted = false")
    long countByIdInAndDeletedFalse(@Param("ids") Set<Integer> ids);
}

