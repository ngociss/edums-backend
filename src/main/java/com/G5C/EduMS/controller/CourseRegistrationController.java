package com.G5C.EduMS.controller;


import com.G5C.EduMS.dto.request.CourseRegistrationRequest;
import com.G5C.EduMS.dto.request.CourseRegistrationSwitchRequest;
import com.G5C.EduMS.dto.response.AvailableCourseSectionResponse;
import com.G5C.EduMS.dto.response.CourseRegistrationResponse;
import com.G5C.EduMS.service.CourseRegistrationService;
import com.G5C.EduMS.dto.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course-registrations")
@RequiredArgsConstructor
public class CourseRegistrationController {

    private final CourseRegistrationService courseRegistrationService;

    @GetMapping("/available-sections")
    public ResponseEntity<ResponseData<List<AvailableCourseSectionResponse>>> getAvailableSections(
            @RequestParam(required = false) Integer facultyId,
            @RequestParam(required = false) Integer courseId,
            @RequestParam(required = false) Integer semesterId,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(
                ResponseData.success(
                        "Lấy danh sách lớp học phần có thể đăng ký thành công",
                        courseRegistrationService.getAvailableSections(facultyId, courseId, semesterId, keyword),
                        200
                )
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseData<CourseRegistrationResponse>> register(
            @Valid @RequestBody CourseRegistrationRequest request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseData.success("Đăng ký học phần thành công", courseRegistrationService.register(request), 201));
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseData<List<CourseRegistrationResponse>>> getMyRegistrations(
            @RequestParam(required = false) Integer semesterId
    ) {
        return ResponseEntity.ok(
                ResponseData.success(
                        "Lấy danh sách học phần đã đăng ký của sinh viên hiện tại thành công",
                        courseRegistrationService.getCurrentStudentRegistrations(semesterId),
                        200
                )
        );
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<ResponseData<List<CourseRegistrationResponse>>> getStudentRegistrations(
            @PathVariable Integer studentId,
            @RequestParam(required = false) Integer semesterId
    ) {
        return ResponseEntity.ok(
                ResponseData.success(
                        "Lấy danh sách học phần đã đăng ký của sinh viên thành công",
                        courseRegistrationService.getStudentRegistrations(studentId, semesterId),
                        200
                )
        );
    }

    @PatchMapping("/{registrationId}/cancel")
    public ResponseEntity<ResponseData<CourseRegistrationResponse>> cancel(@PathVariable Integer registrationId) {
        return ResponseEntity.ok(
                ResponseData.success(
                        "Hủy đăng ký học phần thành công",
                        courseRegistrationService.cancel(registrationId),
                        200
                )
        );
    }

    @PostMapping("/{registrationId}/switch")
    public ResponseEntity<ResponseData<CourseRegistrationResponse>> switchSection(
            @PathVariable Integer registrationId,
            @Valid @RequestBody CourseRegistrationSwitchRequest request
    ) {
        return ResponseEntity.ok(
                ResponseData.success(
                        "Chuyển lớp học phần thành công",
                        courseRegistrationService.switchSection(registrationId, request),
                        200
                )
        );
    }
}
