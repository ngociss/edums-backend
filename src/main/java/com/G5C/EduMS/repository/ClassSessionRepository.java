package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.ClassSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    boolean existsByRoomId(Integer roomId);

    boolean existsByRecurringScheduleIdAndSessionDateAndDeletedFalse(
            Integer recurringScheduleId,
            LocalDate sessionDate
    );

    @Query("""
        SELECT COUNT(cs) > 0 FROM ClassSession cs
        WHERE cs.recurringSchedule.id = :scheduleId
          AND cs.deleted = false
          AND EXISTS (
              SELECT a FROM Attendance a
              WHERE a.session.id = cs.id
                AND a.deleted = false
          )
    """)
    boolean existsSessionWithAttendanceByScheduleId(@Param("scheduleId") Integer scheduleId);

    @Query("""
        SELECT cs
        FROM ClassSession cs
        JOIN FETCH cs.recurringSchedule rs
        JOIN FETCH rs.section sec
        JOIN FETCH sec.course c
        LEFT JOIN FETCH cs.room r
        WHERE sec.lecturer.id = :lecturerId
          AND sec.semester.id = :semesterId
          AND cs.deleted = false
          AND rs.deleted = false
          AND sec.deleted = false
        ORDER BY cs.sessionDate ASC, cs.startPeriod ASC
    """)
    List<ClassSession> findScheduleByLecturerAndSemester(
            @Param("lecturerId") Integer lecturerId,
            @Param("semesterId") Integer semesterId
    );

    @Modifying
    @Query("""
        DELETE FROM ClassSession cs
        WHERE cs.recurringSchedule.id = :scheduleId
    """)
    int deleteByRecurringScheduleId(@Param("scheduleId") Integer scheduleId);
}
