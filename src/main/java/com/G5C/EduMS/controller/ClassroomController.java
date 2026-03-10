package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.ClassroomRequest;
import com.G5C.EduMS.dto.reponse.ClassroomResponse;
import com.G5C.EduMS.dto.reponse.ResponseData;
import com.G5C.EduMS.service.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classrooms")
@RequiredArgsConstructor
@Tag(name = "Classroom", description = "APIs for managing classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping
    @Operation(summary = "Get all classrooms")
    public ResponseEntity<ResponseData<List<ClassroomResponse>>> getAll() {
        return ResponseEntity.ok(ResponseData.success("Success", classroomService.getAll(), 200));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get classroom by ID")
    public ResponseEntity<ResponseData<ClassroomResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ResponseData.success("Success", classroomService.getById(id), 200));
    }

    @PostMapping
    @Operation(summary = "Create new classroom")
    public ResponseEntity<ResponseData<ClassroomResponse>> create(
            @Valid @RequestBody ClassroomRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseData.success("Classroom created successfully", classroomService.create(request), 201));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update classroom")
    public ResponseEntity<ResponseData<ClassroomResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ClassroomRequest request) {
        return ResponseEntity.ok(
                ResponseData.success("Classroom updated successfully", classroomService.update(id, request), 200));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete classroom")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Integer id) {
        classroomService.delete(id);
        return ResponseEntity.ok(ResponseData.success("Classroom deleted successfully", null, 200));
    }
}

