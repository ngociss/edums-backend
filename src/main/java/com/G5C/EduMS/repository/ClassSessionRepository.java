package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.ClassSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassSessionRepository extends JpaRepository<ClassSession, Integer> {

    Optional<ClassSession> findByIdAndDeletedFalse(Integer id);

    List<ClassSession> findAllBySectionIdAndDeletedFalse(Integer sectionId);

    List<ClassSession> findAllByRecurringScheduleIdAndDeletedFalse(Integer recurringScheduleId);

    /** Dùng để ràng buộc khi xóa Classroom */
    boolean existsByRoomId(Integer roomId);

    /** Kiểm tra trùng ngày trong cùng section (tránh sinh trùng ClassSession) */
    boolean existsBySectionIdAndSessionDateAndDeletedFalse(Integer sectionId, LocalDate sessionDate);

    /**
     * Kiểm tra xem có buổi học nào của schedule này đã có điểm danh chưa.
     * Nếu có → không được xóa RecurringSchedule.
     */
    @Query("""
        SELECT COUNT(cs) > 0 FROM ClassSession cs
        WHERE cs.recurringSchedule.id = :scheduleId
          AND cs.deleted = false
          AND EXISTS (
              SELECT a FROM Attendance a
              WHERE a.session.id = cs.id
                AND a.deleted    = false
          )
    """)
    boolean existsSessionWithAttendanceByScheduleId(@Param("scheduleId") Integer scheduleId);
}
