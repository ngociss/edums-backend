package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.response.GradeReportResponse;
import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.dto.request.GradeReportRequest;
import com.G5C.EduMS.service.GradeReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Grade Report", description = "APIs for managing grade reports")
public class GradeReportController {

    private final GradeReportService gradeReportService;

    @PostMapping("/grade-reports")
    @Operation(summary = "Create grade report for a registration")
    public ResponseEntity<ResponseData<GradeReportResponse>> create(
            @Valid @RequestBody GradeReportRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseData.success("Grade report created successfully",
                gradeReportService.create(request), 201));
    }

    @PutMapping("/grade-reports/{id}")
    @Operation(summary = "Update grade report")
    public ResponseEntity<ResponseData<GradeReportResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody GradeReportRequest request) {
        return ResponseEntity.ok(
            ResponseData.success("Grade report updated successfully",
                gradeReportService.update(id, request), 200));
    }

    @DeleteMapping("/grade-reports/{id}")
    @Operation(summary = "Soft delete grade report")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Integer id) {
        gradeReportService.delete(id);
        return ResponseEntity.ok(
            ResponseData.success("Grade report deleted successfully", null, 200));
    }

    @GetMapping("/course-sections/{sectionId}/grade-reports")
    @Operation(summary = "Lecturer gets all grade reports by section")
    public ResponseEntity<ResponseData<List<GradeReportResponse>>> getBySection(
            @PathVariable Integer sectionId) {
        return ResponseEntity.ok(
            ResponseData.success("Success",
                gradeReportService.getBySection(sectionId), 200));
    }


//    @GetMapping("/students/{studentId}/grade-reports")
//    @Operation(summary = "Legacy route for admin/manager")
//    public ResponseEntity<ResponseData<List<GradeReportResponse>>> getByStudent(
//            @PathVariable Integer studentId) {
//        return ResponseEntity.ok(
//            ResponseData.success("Success",
//                gradeReportService.getByStudent(studentId), 200));
//    }

    @GetMapping("/students/me/grade-reports")
    @Operation(summary = "Student views their own grade reports by authenticated account")
    public ResponseEntity<ResponseData<List<GradeReportResponse>>> getMyGradeReports(
            Authentication authentication) {
        return ResponseEntity.ok(
            ResponseData.success("Success",
                gradeReportService.getCurrentStudentGradeReports(authentication.getName()), 200));
    }

    @GetMapping("/students/me/grade-reports/{id}")
    @Operation(summary = "Student views their own grade report detail")
    public ResponseEntity<ResponseData<GradeReportResponse>> getMyGradeReportDetail(
            @PathVariable Integer id,
            Authentication authentication) {
        return ResponseEntity.ok(
            ResponseData.success("Success",
                gradeReportService.getCurrentStudentGradeReportById(authentication.getName(), id), 200));
    }

    @GetMapping("/grade-reports/{id}")
    @Operation(summary = "Get grade report detail by ID")
    public ResponseEntity<ResponseData<GradeReportResponse>> getById(
            @PathVariable Integer id) {
        return ResponseEntity.ok(
            ResponseData.success("Success",
                gradeReportService.getById(id), 200));
    }
}
