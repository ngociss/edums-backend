package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    List<Faculty> findAllByDeletedFalse();

    Optional<Faculty> findByIdAndDeletedFalse(Integer id);

    boolean existsByFacultyNameAndDeletedFalse(String facultyName);

    boolean existsByFacultyCodeAndDeletedFalse(String facultyCode);

    boolean existsByFacultyNameAndIdNotAndDeletedFalse(
            String facultyName,
            Integer id
    );

    boolean existsByFacultyCodeAndIdNotAndDeletedFalse(
            String facultyCode,
            Integer id
    );

}

