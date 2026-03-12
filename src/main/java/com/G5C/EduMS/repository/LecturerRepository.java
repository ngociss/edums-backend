package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {

    List<Lecturer> findAllByDeletedFalse();

    Optional<Lecturer> findByIdAndDeletedFalse(Integer id);
}

