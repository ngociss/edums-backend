package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.CourseRequest;
import com.G5C.EduMS.dto.reponse.CourseResponse;
import com.G5C.EduMS.dto.reponse.ResponseData;
import com.G5C.EduMS.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Tag(name = "Course", description = "APIs for managing courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    @Operation(summary = "Get all courses")
    public ResponseEntity<ResponseData<List<CourseResponse>>> getAll() {
        return ResponseEntity.ok(ResponseData.success("Success", courseService.getAll(), 200));
    }

    @GetMapping("/faculty/{facultyId}")
    @Operation(summary = "Get all courses by faculty")
    public ResponseEntity<ResponseData<List<CourseResponse>>> getAllByFaculty(@PathVariable Integer facultyId) {
        return ResponseEntity.ok(ResponseData.success("Success", courseService.getAllByFaculty(facultyId), 200));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID")
    public ResponseEntity<ResponseData<CourseResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ResponseData.success("Success", courseService.getById(id), 200));
    }

    @PostMapping
    @Operation(summary = "Create new course")
    public ResponseEntity<ResponseData<CourseResponse>> create(
            @Valid @RequestBody CourseRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseData.success("Course created successfully", courseService.create(request), 201));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course")
    public ResponseEntity<ResponseData<CourseResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(
                ResponseData.success("Course updated successfully", courseService.update(id, request), 200));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete course")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Integer id) {
        courseService.delete(id);
        return ResponseEntity.ok(ResponseData.success("Course deleted successfully", null, 200));
    }
}

