package com.G5C.EduMS.controller;


import com.G5C.EduMS.dto.request.CourseRegistrationRequest;
import com.G5C.EduMS.dto.request.CourseRegistrationSwitchRequest;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseData<CourseRegistrationResponse>> register(
            @Valid @RequestBody CourseRegistrationRequest request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseData.success("Course registration successful", courseRegistrationService.register(request), 201));
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseData<List<CourseRegistrationResponse>>> getMyRegistrations(
            @RequestParam(required = false) Integer semesterId
    ) {
        return ResponseEntity.ok(
                ResponseData.success(
                        "Fetched current student registrations successfully",
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
                        "Fetched student registrations successfully",
                        courseRegistrationService.getStudentRegistrations(studentId, semesterId),
                        200
                )
        );
    }

    @PatchMapping("/{registrationId}/cancel")
    public ResponseEntity<ResponseData<CourseRegistrationResponse>> cancel(@PathVariable Integer registrationId) {
        return ResponseEntity.ok(
                ResponseData.success(
                        "Course registration cancelled successfully",
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
                        "Course registration switched successfully",
                        courseRegistrationService.switchSection(registrationId, request),
                        200
                )
        );
    }
}
