package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.FacultyRequest;
import com.G5C.EduMS.dto.reponse.FacultyResponse;
import com.G5C.EduMS.dto.reponse.ResponseData;
import com.G5C.EduMS.service.FacultyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/faculties")
@RequiredArgsConstructor
@Tag(name = "Faculty", description = "APIs for managing faculties")
public class FacultyController {

    private final FacultyService facultyService;

    @GetMapping
    @Operation(summary = "Get all faculties")
    public ResponseEntity<ResponseData<List<FacultyResponse>>> getAll() {
        return ResponseEntity.ok(ResponseData.success("Success", facultyService.getAll(), 200));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get faculty by ID")
    public ResponseEntity<ResponseData<FacultyResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ResponseData.success("Success", facultyService.getById(id), 200));
    }

    @PostMapping
    @Operation(summary = "Create new faculty")
    public ResponseEntity<ResponseData<FacultyResponse>> create(
            @Valid @RequestBody FacultyRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseData.success("Faculty created successfully", facultyService.create(request), 201));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update faculty")
    public ResponseEntity<ResponseData<FacultyResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody FacultyRequest request) {
        return ResponseEntity.ok(
                ResponseData.success("Faculty updated successfully", facultyService.update(id, request), 200));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete faculty")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Integer id) {
        facultyService.delete(id);
        return ResponseEntity.ok(ResponseData.success("Faculty deleted successfully", null, 200));
    }
}
