package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.ClassSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassSessionRepository extends JpaRepository<ClassSession, Integer> {

    // Kiểm tra ràng buộc trước khi xóa Classroom
    boolean existsByRoomId(Integer roomId);
}

