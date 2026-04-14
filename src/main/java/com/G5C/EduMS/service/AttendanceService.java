package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.response.AttendanceResponse;
import com.G5C.EduMS.dto.request.AttendanceBatchRequest;
import com.G5C.EduMS.dto.request.AttendanceUpdateRequest;

import java.util.List;

public interface AttendanceService {

    // KAN-43 — Giảng viên
    List<AttendanceResponse> getBySession(Integer sessionId);

    List<AttendanceResponse> createBatch(Integer sessionId, AttendanceBatchRequest request);
    List<AttendanceResponse> syncSessionAttendance(Integer sessionId);

    AttendanceResponse update(Integer id, AttendanceUpdateRequest request);

    void delete(Integer id);

    // KAN-45 — Sinh viên / Phụ huynh
    List<AttendanceResponse> getCurrentStudentAttendances(String username);

    List<AttendanceResponse> getByStudent(Integer studentId);

    List<AttendanceResponse> getByCurrentGuardianAndStudent(String username, Integer studentId);

    List<AttendanceResponse> getByGuardianAndStudent(Integer guardianId, Integer studentId);
}
