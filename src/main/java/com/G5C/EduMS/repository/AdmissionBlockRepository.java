package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.AdmissionBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AdmissionBlockRepository extends JpaRepository<AdmissionBlock, Integer>{
    List<AdmissionBlock> findAllByDeletedFalse();

    List<AdmissionBlock> findAllByIdInAndDeletedFalse(Set<Integer> blockIds);

    // Trả về true nếu tên khối đã tồn tại (ngoại trừ ID đang được cập nhật)
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM AdmissionBlock b " +
            "WHERE b.deleted = false AND b.blockName = :name " +
            "AND (:currentId IS NULL OR b.id != :currentId)")
    boolean existsByBlockNameAndNotId(@Param("name") String name, @Param("currentId") Integer currentId);

    @Query("SELECT COUNT(b) FROM AdmissionBlock b WHERE b.id IN :ids AND b.deleted = false")
    long countByIdInAndDeletedFalse(@Param("ids") Set<Integer> ids);
}
