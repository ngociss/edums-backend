package com.G5C.EduMS.controller;


import com.G5C.EduMS.dto.request.CourseRegistrationRequest;

import com.G5C.EduMS.dto.response.CourseRegistrationResponse;
import com.G5C.EduMS.service.CourseRegistrationService;
import com.G5C.EduMS.dto.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}