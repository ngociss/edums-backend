package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.GradeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeDetailRepository extends JpaRepository<GradeDetail, Integer> {

    Optional<GradeDetail> findByIdAndDeletedFalse(Integer id);

    List<GradeDetail> findAllByReportIdAndDeletedFalse(Integer reportId);

    boolean existsByReportIdAndComponentIdAndDeletedFalse(Integer reportId, Integer componentId);
}
