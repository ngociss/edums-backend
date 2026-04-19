package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.dto.response.StudentScheduleSemesterOptionResponse;
import com.G5C.EduMS.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
@Tag(name = "Schedule Management", description = "Cac API lien quan den tra cuu thoi khoa bieu")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "Xem thoi khoa bieu giang day", description = "Danh cho giang vien")
    @GetMapping("/lecturers/me")
    public ResponseEntity<ResponseData> getMyLecturerSchedule(
            @RequestParam Integer semesterId
    ) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var schedule = scheduleService.getMyLecturerSchedule(currentUsername, semesterId);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Lay thoi khoa bieu thanh cong", schedule));
    }

    @Operation(summary = "Lay danh sach hoc ky cho thoi khoa bieu sinh vien")
    @GetMapping("/students/me/semester-options")
    public ResponseEntity<ResponseData<List<StudentScheduleSemesterOptionResponse>>> getMyStudentSemesterOptions() {
        return ResponseEntity.ok(
                ResponseData.success(
                        "Lay danh sach hoc ky cho thoi khoa bieu thanh cong",
                        scheduleService.getMyStudentSemesterOptions(),
                        200
                )
        );
    }
}
