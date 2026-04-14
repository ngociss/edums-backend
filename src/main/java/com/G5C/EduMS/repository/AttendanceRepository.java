package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    Optional<Attendance> findByIdAndDeletedFalse(Integer id);
    Optional<Attendance> findBySession_IdAndRegistration_Id(Integer sessionId, Integer registrationId);

    @Query("SELECT a FROM Attendance a WHERE a.session.id = :sessionId AND a.deleted = false")
    List<Attendance> findAllBySessionId(@Param("sessionId") Integer sessionId);

    @Query("SELECT a FROM Attendance a WHERE a.registration.student.id = :studentId AND a.deleted = false ORDER BY a.session.sessionDate DESC")
    List<Attendance> findAllByStudentId(@Param("studentId") Integer studentId);

    @Query("SELECT COUNT(a) > 0 FROM Attendance a WHERE a.session.id = :sessionId AND a.registration.id = :registrationId AND a.deleted = false")
    boolean existsBySessionIdAndRegistrationId(
        @Param("sessionId") Integer sessionId,
        @Param("registrationId") Integer registrationId);
}
