package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.SpecializationRequest;
import com.G5C.EduMS.dto.response.SpecializationResponse;
import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.service.SpecializationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/specializations")
@RequiredArgsConstructor
@Tag(name = "Specialization", description = "APIs for managing specializations")
public class SpecializationController {

    private final SpecializationService specializationService;

    @GetMapping
    @Operation(summary = "Get all specializations")
    public ResponseEntity<ResponseData<List<SpecializationResponse>>> getAll() {
        return ResponseEntity.ok(ResponseData.success("Success", specializationService.getAll(), 200));
    }

    @GetMapping("/major/{majorId}")
    @Operation(summary = "Get all specializations by major")
    public ResponseEntity<ResponseData<List<SpecializationResponse>>> getAllByMajor(@PathVariable Integer majorId) {
        return ResponseEntity.ok(ResponseData.success("Success", specializationService.getAllByMajor(majorId), 200));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get specialization by ID")
    public ResponseEntity<ResponseData<SpecializationResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ResponseData.success("Success", specializationService.getById(id), 200));
    }

    @PostMapping
    @Operation(summary = "Create new specialization")
    public ResponseEntity<ResponseData<SpecializationResponse>> create(
            @Valid @RequestBody SpecializationRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseData.success("Specialization created successfully", specializationService.create(request), 201));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update specialization")
    public ResponseEntity<ResponseData<SpecializationResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody SpecializationRequest request) {
        return ResponseEntity.ok(
                ResponseData.success("Specialization updated successfully", specializationService.update(id, request), 200));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete specialization")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Integer id) {
        specializationService.delete(id);
        return ResponseEntity.ok(ResponseData.success("Specialization deleted successfully", null, 200));
    }
}

