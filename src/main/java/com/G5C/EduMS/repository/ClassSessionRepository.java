package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.ClassSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassSessionRepository extends JpaRepository<ClassSession, Integer> {

    Optional<ClassSession> findByIdAndDeletedFalse(Integer id);

    @Query("""
        SELECT cs FROM ClassSession cs
        JOIN cs.recurringSchedule rs
        WHERE rs.section.id = :sectionId
          AND cs.deleted = false
          AND rs.deleted = false
    """)
    List<ClassSession> findAllBySectionIdAndDeletedFalse(@Param("sectionId") Integer sectionId);

    List<ClassSession> findAllByRecurringScheduleIdAndDeletedFalse(Integer recurringScheduleId);

    /** Dùng để ràng buộc khi xóa Classroom */
    boolean existsByRoomId(Integer roomId);

    /** Kiểm tra trùng ngày trong cùng section (tránh sinh trùng ClassSession) */
    boolean existsByRecurringScheduleIdAndSessionDateAndDeletedFalse(
            Integer recurringScheduleId,
            LocalDate sessionDate
    );

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

    // Lấy thời khóa biểu cho giảng viên theo khoảng thời gian
    @Query("SELECT cs FROM ClassSession cs " +
            "JOIN FETCH cs.recurringSchedule rs " +
            "JOIN FETCH rs.section sec " +
            "JOIN FETCH sec.course c " +
            "LEFT JOIN FETCH cs.room r " +
            "WHERE sec.lecturer.id = :lecturerId " +
            "AND cs.sessionDate BETWEEN :startDate AND :endDate " +
            "AND cs.deleted = false " +
            "AND rs.deleted = false " +
            "AND sec.deleted = false " +
            "ORDER BY cs.sessionDate ASC, cs.startPeriod ASC")
    List<ClassSession> findScheduleByLecturerAndDateRange(
            @Param("lecturerId") Integer lecturerId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Modifying
    @Query("""
    DELETE FROM ClassSession cs
    WHERE cs.recurringSchedule.id = :scheduleId
""")
    int deleteByRecurringScheduleId(@Param("scheduleId") Integer scheduleId);
}
