package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.RecurringSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurringScheduleRepository extends JpaRepository<RecurringSchedule, Integer> {

    // Kiểm tra ràng buộc trước khi xóa Classroom
    boolean existsByRoomId(Integer roomId);
}

