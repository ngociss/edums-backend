package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Cohort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CohortRepository extends JpaRepository<Cohort, Integer> {

    List<Cohort> findAllByDeletedFalse();

    Optional<Cohort> findByIdAndDeletedFalse(Integer id);
}

