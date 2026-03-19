package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.MajorRequest;
import com.G5C.EduMS.dto.response.MajorResponse;
import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.service.MajorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/majors")
@RequiredArgsConstructor
@Tag(name = "Major", description = "APIs for managing majors")
public class MajorController {

    private final MajorService majorService;

    @GetMapping
    @Operation(summary = "Get all majors")
    public ResponseEntity<ResponseData<List<MajorResponse>>> getAll() {
        return ResponseEntity.ok(ResponseData.success("Success", majorService.getAll(), 200));
    }

    @GetMapping("/faculty/{facultyId}")
    @Operation(summary = "Get all majors by faculty")
    public ResponseEntity<ResponseData<List<MajorResponse>>> getAllByFaculty(@PathVariable Integer facultyId) {
        return ResponseEntity.ok(ResponseData.success("Success", majorService.getAllByFaculty(facultyId), 200));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get major by ID")
    public ResponseEntity<ResponseData<MajorResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ResponseData.success("Success", majorService.getById(id), 200));
    }

    @PostMapping
    @Operation(summary = "Create new major")
    public ResponseEntity<ResponseData<MajorResponse>> create(
            @Valid @RequestBody MajorRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseData.success("Major created successfully", majorService.create(request), 201));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update major")
    public ResponseEntity<ResponseData<MajorResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody MajorRequest request) {
        return ResponseEntity.ok(
                ResponseData.success("Major updated successfully", majorService.update(id, request), 200));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete major")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Integer id) {
        majorService.delete(id);
        return ResponseEntity.ok(ResponseData.success("Major deleted successfully", null, 200));
    }
}
