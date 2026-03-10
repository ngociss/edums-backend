package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    List<Specialization> findAllByDeletedFalse();

    List<Specialization> findAllByMajorIdAndDeletedFalse(Integer majorId);

    Optional<Specialization> findByIdAndDeletedFalse(Integer id);

    boolean existsBySpecializationNameAndMajorIdAndDeletedFalse(String specializationName, Integer majorId);

    // Kiểm tra ràng buộc trước khi xóa Major
    boolean existsByMajorIdAndDeletedFalse(Integer majorId);

    boolean existsBySpecializationNameAndMajorIdAndIdNotAndDeletedFalse(
            String specializationName,
            Integer majorId,
            Integer id
    );
}

