package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.CourseSectionRequest;
import com.G5C.EduMS.dto.reponse.CourseSectionResponse;
import com.G5C.EduMS.dto.reponse.ResponseData;
import com.G5C.EduMS.service.CourseSectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course-sections")
@RequiredArgsConstructor
@Tag(name = "Course Section", description = "APIs for managing course sections (lớp học phần)")
public class CourseSectionController {

    private final CourseSectionService courseSectionService;

    @GetMapping
    @Operation(summary = "Get all course sections")
    public ResponseEntity<ResponseData<List<CourseSectionResponse>>> getAll() {
        return ResponseEntity.ok(ResponseData.success("Success", courseSectionService.getAll(), 200));
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get course sections by course")
    public ResponseEntity<ResponseData<List<CourseSectionResponse>>> getAllByCourse(@PathVariable Integer courseId) {
        return ResponseEntity.ok(ResponseData.success("Success", courseSectionService.getAllByCourse(courseId), 200));
    }

    @GetMapping("/semester/{semesterId}")
    @Operation(summary = "Get course sections by semester")
    public ResponseEntity<ResponseData<List<CourseSectionResponse>>> getAllBySemester(@PathVariable Integer semesterId) {
        return ResponseEntity.ok(ResponseData.success("Success", courseSectionService.getAllBySemester(semesterId), 200));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get course section by ID")
    public ResponseEntity<ResponseData<CourseSectionResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ResponseData.success("Success", courseSectionService.getById(id), 200));
    }

    @PostMapping
    @Operation(summary = "Create new course section")
    public ResponseEntity<ResponseData<CourseSectionResponse>> create(
            @Valid @RequestBody CourseSectionRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseData.success("Course section created successfully", courseSectionService.create(request), 201));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course section")
    public ResponseEntity<ResponseData<CourseSectionResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody CourseSectionRequest request) {
        return ResponseEntity.ok(
                ResponseData.success("Course section updated successfully", courseSectionService.update(id, request), 200));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete course section (only DRAFT or CANCELLED)")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Integer id) {
        courseSectionService.delete(id);
        return ResponseEntity.ok(ResponseData.success("Course section deleted successfully", null, 200));
    }
}

