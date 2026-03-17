package com.G5C.EduMS.repository;

import com.G5C.EduMS.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
}
