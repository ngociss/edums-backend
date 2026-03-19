package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
@Tag(name = "Schedule Management", description = "Các API liên quan đến tra cứu thời khóa biểu")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "Xem thời khóa biểu giảng dạy (Dành cho Giảng viên)",
            description = "Lấy lịch dạy trong một khoảng thời gian. Yêu cầu truyền Token JWT của giảng viên.")
    @GetMapping("/lecturers/me")
    public ResponseEntity<ResponseData> getMyLecturerSchedule(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        // Lấy username từ SecurityContext (Token JWT)
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        var schedule = scheduleService.getMyLecturerSchedule(currentUsername, startDate, endDate);

        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Lấy thời khóa biểu thành công", schedule));
    }
}
