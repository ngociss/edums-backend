package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.reponse.AttendanceResponse;
import com.G5C.EduMS.dto.reponse.ResponseData;
import com.G5C.EduMS.dto.request.AttendanceBatchRequest;
import com.G5C.EduMS.dto.request.AttendanceUpdateRequest;
import com.G5C.EduMS.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Attendance", description = "APIs for managing attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    // ===== KAN-43: Giảng viên điểm danh =====

    @GetMapping("/api/v1/class-sessions/{sessionId}/attendances")
    @Operation(summary = "Get attendance list by session")
    public ResponseEntity<ResponseData<List<AttendanceResponse>>> getBySession(
            @PathVariable Integer sessionId) {
        return ResponseEntity.ok(
            ResponseData.success("Success",
                attendanceService.getBySession(sessionId), 200));
    }

    @PostMapping("/api/v1/class-sessions/{sessionId}/attendances/batch")
    @Operation(summary = "Create batch attendance for a session")
    public ResponseEntity<ResponseData<List<AttendanceResponse>>> createBatch(
            @PathVariable Integer sessionId,
            @Valid @RequestBody AttendanceBatchRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseData.success("Attendance recorded successfully",
                attendanceService.createBatch(sessionId, request), 201));
    }

    @PutMapping("/api/v1/attendances/{id}")
    @Operation(summary = "Update attendance record")
    public ResponseEntity<ResponseData<AttendanceResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody AttendanceUpdateRequest request) {
        return ResponseEntity.ok(
            ResponseData.success("Attendance updated successfully",
                attendanceService.update(id, request), 200));
    }

    @DeleteMapping("/api/v1/attendances/{id}")
    @Operation(summary = "Soft delete attendance record")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Integer id) {
        attendanceService.delete(id);
        return ResponseEntity.ok(
            ResponseData.success("Attendance deleted successfully", null, 200));
    }

    @GetMapping("/api/v1/students/{studentId}/attendances")
    @Operation(summary = "Student views their own attendance")
    public ResponseEntity<ResponseData<List<AttendanceResponse>>> getByStudent(
            @PathVariable Integer studentId) {
        return ResponseEntity.ok(
            ResponseData.success("Success",
                attendanceService.getByStudent(studentId), 200));
    }

    @GetMapping("/api/v1/guardians/{guardianId}/students/{studentId}/attendances")
    @Operation(summary = "Guardian views attendance of their child")
    public ResponseEntity<ResponseData<List<AttendanceResponse>>> getByGuardianAndStudent(
            @PathVariable Integer guardianId,
            @PathVariable Integer studentId) {
        return ResponseEntity.ok(
            ResponseData.success("Success",
                attendanceService.getByGuardianAndStudent(guardianId, studentId), 200));
    }
}
