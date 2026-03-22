package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.AdmissionBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionBlockRepository extends JpaRepository<AdmissionBlock, Integer>{
    List<AdmissionBlock> findAllByDeletedFalse();
}
