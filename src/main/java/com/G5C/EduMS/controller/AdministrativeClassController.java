package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.reponse.AdministrativeClassResponse;
import com.G5C.EduMS.dto.reponse.ResponseData;
import com.G5C.EduMS.dto.request.AdministrativeClassRequest;
import com.G5C.EduMS.service.AdministrativeClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/administrative-classes")
@RequiredArgsConstructor
@Tag(name = "Administrative Class", description = "APIs for managing administrative classes")
public class AdministrativeClassController {

    private final AdministrativeClassService administrativeClassService;

    @GetMapping
    @Operation(summary = "Get all administrative classes")
    public ResponseEntity<ResponseData<List<AdministrativeClassResponse>>> getAll() {
        return ResponseEntity.ok(
            ResponseData.success("Success", administrativeClassService.getAll(), 200));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get administrative class by ID")
    public ResponseEntity<ResponseData<AdministrativeClassResponse>> getById(
            @PathVariable Integer id) {
        return ResponseEntity.ok(
            ResponseData.success("Success", administrativeClassService.getById(id), 200));
    }

    @PostMapping
    @Operation(summary = "Create new administrative class")
    public ResponseEntity<ResponseData<AdministrativeClassResponse>> create(
            @Valid @RequestBody AdministrativeClassRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseData.success("Administrative class created successfully",
                administrativeClassService.create(request), 201));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update administrative class")
    public ResponseEntity<ResponseData<AdministrativeClassResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody AdministrativeClassRequest request) {
        return ResponseEntity.ok(
            ResponseData.success("Administrative class updated successfully",
                administrativeClassService.update(id, request), 200));
    }

    // @DeleteMapping("/{id}")
    // @Operation(summary = "Soft delete administrative class")
    // public ResponseEntity<ResponseData<Void>> delete(@PathVariable Integer id) {
    //     administrativeClassService.delete(id);
    //     return ResponseEntity.ok(
    //         ResponseData.success("Administrative class deleted successfully", null, 200));
    // }
}
