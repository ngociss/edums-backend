package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.GradeComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeComponentRepository extends JpaRepository<GradeComponent, Integer> {

    Optional<GradeComponent> findByIdAndDeletedFalse(Integer id);
    List<GradeComponent> findByCourseIdAndDeletedFalse(Integer courseId);
    List<GradeComponent> findByDeletedFalse();
}
