package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.CohortRequest;
import com.G5C.EduMS.dto.response.CohortResponse;
import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.service.CohortService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cohorts")
@RequiredArgsConstructor
@Tag(name = "Cohort", description = "APIs for managing cohorts (niên khóa)")
public class CohortController {

    private final CohortService cohortService;

    @GetMapping
    @Operation(summary = "Get all cohorts")
    public ResponseEntity<ResponseData<List<CohortResponse>>> getAll() {
        return ResponseEntity.ok(
                ResponseData.success("Success", cohortService.getAll(), 200));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get cohort by ID")
    public ResponseEntity<ResponseData<CohortResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                ResponseData.success("Success", cohortService.getById(id), 200));
    }

    @PostMapping
    @Operation(summary = "Create new cohort")
    public ResponseEntity<ResponseData<CohortResponse>> create(@Valid @RequestBody CohortRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseData.success("Cohort created successfully", cohortService.create(request), 201));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update cohort")
    public ResponseEntity<ResponseData<CohortResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody CohortRequest request) {
        return ResponseEntity.ok(
                ResponseData.success("Cohort updated successfully", cohortService.update(id, request), 200));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete cohort")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Integer id) {
        cohortService.delete(id);
        return ResponseEntity.ok(
                ResponseData.success("Cohort deleted successfully", null, 200));
    }
}
