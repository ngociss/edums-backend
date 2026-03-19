package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.response.ClassSessionResponse;
import com.G5C.EduMS.dto.response.RecurringScheduleResponse;
import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.dto.request.RecurringScheduleRequest;
import com.G5C.EduMS.service.RecurringScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recurring-schedules")
@RequiredArgsConstructor
@Tag(name = "Recurring Schedule", description = "Quản lý lịch học lặp lại hàng tuần (RecurringSchedule)")
public class RecurringScheduleController {

    private final RecurringScheduleService recurringScheduleService;

    // ==================== GET ====================

    @GetMapping("/section/{sectionId}")
    @Operation(summary = "Lấy toàn bộ lịch học của một lớp học phần")
    public ResponseEntity<ResponseData<List<RecurringScheduleResponse>>> getBySectionId(
            @PathVariable Integer sectionId) {
        return ResponseEntity.ok(
                ResponseData.success("Success",
                        recurringScheduleService.getBySectionId(sectionId), 200));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy chi tiết lịch học theo ID")
    public ResponseEntity<ResponseData<RecurringScheduleResponse>> getById(
            @PathVariable Integer id) {
        return ResponseEntity.ok(
                ResponseData.success("Success",
                        recurringScheduleService.getById(id), 200));
    }

    @GetMapping("/{id}/sessions")
    @Operation(summary = "Lấy danh sách buổi học (ClassSession) được sinh từ lịch này")
    public ResponseEntity<ResponseData<List<ClassSessionResponse>>> getClassSessions(
            @PathVariable Integer id) {
        return ResponseEntity.ok(
                ResponseData.success("Success",
                        recurringScheduleService.getClassSessions(id), 200));
    }

    // ==================== CREATE ====================

    @PostMapping
    @Operation(summary = "Tạo lịch học mới — tự động sinh ClassSession cho toàn học kỳ")
    public ResponseEntity<ResponseData<RecurringScheduleResponse>> create(
            @Valid @RequestBody RecurringScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseData.success(
                        "Tạo lịch học thành công. Các buổi học đã được sinh tự động.",
                        recurringScheduleService.create(request), 201));
    }

    // ==================== UPDATE ====================

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật lịch học — xóa & sinh lại ClassSession")
    public ResponseEntity<ResponseData<RecurringScheduleResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody RecurringScheduleRequest request) {
        return ResponseEntity.ok(
                ResponseData.success(
                        "Cập nhật lịch học thành công. Các buổi học đã được sinh lại.",
                        recurringScheduleService.update(id, request), 200));
    }

    // ==================== DELETE ====================

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa lịch học và toàn bộ ClassSession liên quan (soft delete)")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Integer id) {
        recurringScheduleService.delete(id);
        return ResponseEntity.ok(
                ResponseData.success("Xóa lịch học thành công.", null, 200));
    }
}

