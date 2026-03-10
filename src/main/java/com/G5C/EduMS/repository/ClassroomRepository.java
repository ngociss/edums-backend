package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {

    List<Classroom> findAllByDeletedFalse();

    Optional<Classroom> findByIdAndDeletedFalse(Integer id);

    boolean existsByRoomNameAndDeletedFalse(String roomName);

    boolean existsByRoomNameAndIdNotAndDeletedFalse(String roomName, Integer id);
}

