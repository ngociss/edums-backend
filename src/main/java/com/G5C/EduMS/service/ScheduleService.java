package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.response.LecturerScheduleResponse;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    List<LecturerScheduleResponse> getMyLecturerSchedule(String username, LocalDate startDate, LocalDate endDate);
}
