package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.AdmissionBlock;
import com.G5C.EduMS.model.AdmissionPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AdmissionBlockRepository extends JpaRepository<AdmissionBlock, Integer>{
    List<AdmissionBlock> findAllByDeletedFalse();

    List<AdmissionBlock> findAllByIdInAndDeletedFalse(Set<Integer> blockIds);

    @Query("SELECT COUNT(b) FROM AdmissionBlock b WHERE b.id IN :ids AND b.deleted = false")
    long countByIdInAndDeletedFalse(@Param("ids") Set<Integer> ids);

    Optional<AdmissionBlock> findByIdAndDeletedFalse(Integer id);

    boolean existsByBlockNameAndDeletedFalse(String blockName);
}
