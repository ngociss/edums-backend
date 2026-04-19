package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.RecurringSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecurringScheduleRepository extends JpaRepository<RecurringSchedule, Integer> {

    Optional<RecurringSchedule> findByIdAndDeletedFalse(Integer id);

    List<RecurringSchedule> findAllBySectionIdAndDeletedFalse(Integer sectionId);

    /** Dùng để ràng buộc khi xóa Classroom */
    boolean existsByRoomId(Integer roomId);

    /** Dùng để ràng buộc khi xóa CourseSection */
    boolean existsBySectionIdAndDeletedFalse(Integer sectionId);

    // ==================== Conflict Checks ====================

    /**
     * Kiểm tra xung đột trong cùng CourseSection:
     * cùng ngày, tiết chồng nhau
     */
    @Query("""
        SELECT COUNT(rs) > 0 FROM RecurringSchedule rs
        WHERE rs.section.id = :sectionId
          AND rs.dayOfWeek  = :dayOfWeek
          AND rs.deleted    = false
          AND rs.id        != :excludeId
          AND rs.startPeriod <= :endPeriod
          AND rs.endPeriod   >= :startPeriod
          AND rs.startWeek   <= :endWeek
          AND rs.endWeek     >= :startWeek
    """)
    boolean existsConflictInSection(
            @Param("sectionId")   Integer sectionId,
            @Param("dayOfWeek")   Integer dayOfWeek,
            @Param("startPeriod") Integer startPeriod,
            @Param("endPeriod")   Integer endPeriod,
            @Param("startWeek")   Integer startWeek,
            @Param("endWeek")     Integer endWeek,
            @Param("excludeId")   Integer excludeId
    );

    /**
     * Kiểm tra xung đột phòng học:
     * cùng phòng, cùng ngày, tiết chồng nhau
     */
    @Query("""
        SELECT COUNT(rs) > 0 FROM RecurringSchedule rs
        WHERE rs.room.id     = :classroomId
          AND rs.section.semester.id = :semesterId
          AND rs.dayOfWeek   = :dayOfWeek
          AND rs.deleted     = false
          AND rs.id         != :excludeId
          AND rs.startPeriod <= :endPeriod
          AND rs.endPeriod   >= :startPeriod
          AND rs.startWeek   <= :endWeek
          AND rs.endWeek     >= :startWeek
    """)
    boolean existsConflictInClassroom(
            @Param("classroomId") Integer classroomId,
            @Param("semesterId")  Integer semesterId,
            @Param("dayOfWeek")   Integer dayOfWeek,
            @Param("startPeriod") Integer startPeriod,
            @Param("endPeriod")   Integer endPeriod,
            @Param("startWeek")   Integer startWeek,
            @Param("endWeek")     Integer endWeek,
            @Param("excludeId")   Integer excludeId
    );

    /**
     * Kiểm tra xung đột giảng viên:
     * cùng giảng viên, cùng học kỳ, cùng ngày, tiết chồng nhau
     */
    @Query("""
        SELECT COUNT(rs) > 0 FROM RecurringSchedule rs
        WHERE rs.section.lecturer.id  = :lecturerId
          AND rs.section.semester.id  = :semesterId
          AND rs.dayOfWeek            = :dayOfWeek
          AND rs.deleted              = false
          AND rs.id                  != :excludeId
          AND rs.startPeriod <= :endPeriod
          AND rs.endPeriod   >= :startPeriod
          AND rs.startWeek   <= :endWeek
          AND rs.endWeek     >= :startWeek
    """)
    boolean existsConflictForLecturer(
            @Param("lecturerId")  Integer lecturerId,
            @Param("semesterId")  Integer semesterId,
            @Param("dayOfWeek")   Integer dayOfWeek,
            @Param("startPeriod") Integer startPeriod,
            @Param("endPeriod")   Integer endPeriod,
            @Param("startWeek")   Integer startWeek,
            @Param("endWeek")     Integer endWeek,
            @Param("excludeId")   Integer excludeId
    );
}
