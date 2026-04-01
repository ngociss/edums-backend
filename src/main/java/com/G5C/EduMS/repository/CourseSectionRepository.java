package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.CourseSection;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseSectionRepository extends JpaRepository<CourseSection, Integer> {

    List<CourseSection> findAllByDeletedFalse();

    List<CourseSection> findAllByCourseIdAndDeletedFalse(Integer courseId);

    List<CourseSection> findAllBySemesterIdAndDeletedFalse(Integer semesterId);

    @Query("""
        SELECT cs
        FROM CourseSection cs
        JOIN cs.course c
        LEFT JOIN cs.lecturer l
        WHERE cs.deleted = false
          AND cs.semester.deleted = false
          AND c.deleted = false
          AND c.status = com.G5C.EduMS.common.enums.CourseStatus.ACTIVE
          AND cs.status = com.G5C.EduMS.common.enums.CourseSectionStatus.OPEN
          AND EXISTS (
              SELECT 1
              FROM RegistrationPeriod rp
              WHERE rp.semester.id = cs.semester.id
                AND rp.deleted = false
                AND rp.status = com.G5C.EduMS.common.enums.RegistrationPeriodStatus.OPEN
                AND rp.startTime <= :now
                AND rp.endTime >= :now
          )
          AND (:facultyId IS NULL OR c.faculty.id = :facultyId)
          AND (:courseId IS NULL OR c.id = :courseId)
          AND (:semesterId IS NULL OR cs.semester.id = :semesterId)
          AND (
              :keyword IS NULL
              OR TRIM(:keyword) = ''
              OR LOWER(COALESCE(c.courseCode, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
              OR LOWER(COALESCE(c.courseName, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
              OR LOWER(COALESCE(cs.sectionCode, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
              OR LOWER(COALESCE(cs.displayName, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
              OR LOWER(COALESCE(l.fullName, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
        ORDER BY cs.semester.semesterNumber DESC, cs.semester.academicYear DESC, c.courseCode ASC, cs.sectionCode ASC
    """)
    List<CourseSection> findAvailableForRegistration(
            @Param("now") LocalDateTime now,
            @Param("facultyId") Integer facultyId,
            @Param("courseId") Integer courseId,
            @Param("semesterId") Integer semesterId,
            @Param("keyword") String keyword
    );

    Optional<CourseSection> findByIdAndDeletedFalse(Integer id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT cs FROM CourseSection cs WHERE cs.id = :id AND cs.deleted = false")
    Optional<CourseSection> findByIdAndDeletedFalseForUpdate(@Param("id") Integer id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT cs
        FROM CourseSection cs
        WHERE cs.course.id = :courseId
          AND cs.semester.id = :semesterId
          AND cs.deleted = false
    """)
    List<CourseSection> findAllByCourseIdAndSemesterIdAndDeletedFalseForUpdate(
            @Param("courseId") Integer courseId,
            @Param("semesterId") Integer semesterId
    );

    boolean existsBySectionCodeAndCourseIdAndSemesterIdAndIdNotAndDeletedFalse(
            String sectionCode,
            Integer courseId,
            Integer semesterId,
            Integer id
    );
    // Ràng buộc xóa
    boolean existsByCourseIdAndDeletedFalse(Integer courseId);

    boolean existsBySemesterIdAndDeletedFalse(Integer semesterId);


    boolean existsByLecturerIdAndDeletedFalse(Integer lecturerId);
}

