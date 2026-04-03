package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.RegistrationPeriodRequest;
import com.G5C.EduMS.dto.response.RegistrationPeriodResponse;
import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.service.RegistrationPeriodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registration-periods")
@RequiredArgsConstructor
@Tag(name = "Registration Period", description = "APIs for managing course registration periods")
public class RegistrationPeriodController {

    private final RegistrationPeriodService registrationPeriodService;

    @GetMapping
    @Operation(summary = "Get all registration periods")
    public ResponseEntity<ResponseData<List<RegistrationPeriodResponse>>> getAll(
            @RequestParam(required = false) Integer semesterId) {
        return ResponseEntity.ok(
                ResponseData.success("Success", registrationPeriodService.getAll(semesterId), 200)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get registration period by ID")
    public ResponseEntity<ResponseData<RegistrationPeriodResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                ResponseData.success("Success", registrationPeriodService.getById(id), 200)
        );
    }

    @PostMapping
    @Operation(summary = "Create registration period")
    public ResponseEntity<ResponseData<RegistrationPeriodResponse>> create(
            @Valid @RequestBody RegistrationPeriodRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseData.success(
                        "Registration period created successfully",
                        registrationPeriodService.create(request),
                        201
                ));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update registration period")
    public ResponseEntity<ResponseData<RegistrationPeriodResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody RegistrationPeriodRequest request) {
        return ResponseEntity.ok(
                ResponseData.success(
                        "Registration period updated successfully",
                        registrationPeriodService.update(id, request),
                        200
                )
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete registration period")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Integer id) {
        registrationPeriodService.delete(id);
        return ResponseEntity.ok(ResponseData.success("Registration period deleted successfully", null, 200));
    }
}
