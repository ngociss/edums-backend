package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Integer> {

    Optional<Guardian> findByIdAndDeletedFalse(Integer id);
}
