package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.reponse.GradeComponentResponse;
import com.G5C.EduMS.dto.reponse.ResponseData;
import com.G5C.EduMS.dto.request.GradeComponentRequest;
import com.G5C.EduMS.service.GradeComponentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Grade Component", description = "APIs for managing grade components (cấu hình điểm)")
public class GradeComponentController {

    private final GradeComponentService gradeComponentService;

    @GetMapping("/grade-components")
    @Operation(summary = "Get all grade components")
    public ResponseEntity<ResponseData<List<GradeComponentResponse>>> getAll() {
        return ResponseEntity.ok(
                ResponseData.success("Success", gradeComponentService.getAll(), 200));
    }

    @GetMapping("/courses/{courseId}/grade-components")
    @Operation(summary = "Get grade components by course ID")
    public ResponseEntity<ResponseData<List<GradeComponentResponse>>> getByCourse(
            @PathVariable Integer courseId) {
        return ResponseEntity.ok(
                ResponseData.success("Success", gradeComponentService.getByCourse(courseId), 200));
    }

    @GetMapping("/grade-components/{id}")
    @Operation(summary = "Get grade component by ID")
    public ResponseEntity<ResponseData<GradeComponentResponse>> getById(
            @PathVariable Integer id) {
        return ResponseEntity.ok(
                ResponseData.success("Success", gradeComponentService.getById(id), 200));
    }

    @PostMapping("/grade-components")
    @Operation(summary = "Create new grade component")
    public ResponseEntity<ResponseData<GradeComponentResponse>> create(
            @Valid @RequestBody GradeComponentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseData.success("Grade component created successfully", gradeComponentService.create(request), 201));
    }

    @PutMapping("/grade-components/{id}")
    @Operation(summary = "Update grade component")
    public ResponseEntity<ResponseData<GradeComponentResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody GradeComponentRequest request) {
        return ResponseEntity.ok(
                ResponseData.success("Grade component updated successfully", gradeComponentService.update(id, request), 200));
    }

    @DeleteMapping("/grade-components/{id}")
    @Operation(summary = "Soft delete grade component")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Integer id) {
        gradeComponentService.delete(id);
        return ResponseEntity.ok(
                ResponseData.success("Grade component deleted successfully", null, 200));
    }
}
