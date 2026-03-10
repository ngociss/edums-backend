package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MajorRepository extends JpaRepository<Major, Integer> {

    List<Major> findAllByDeletedFalse();

    List<Major> findAllByFacultyIdAndDeletedFalse(Integer facultyId);

    Optional<Major> findByIdAndDeletedFalse(Integer id);

    boolean existsByMajorNameAndFacultyIdAndDeletedFalse(String majorName, Integer facultyId);
    boolean existsByMajorCodeAndFacultyIdAndDeletedFalse(String majorCode, Integer facultyId);
}

