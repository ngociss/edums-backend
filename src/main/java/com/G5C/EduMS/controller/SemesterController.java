package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.SemesterRequest;
import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.dto.response.SemesterResponse;
import com.G5C.EduMS.service.SemesterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/semesters")
@RequiredArgsConstructor
@Tag(name = "Semester", description = "APIs quản lý học kỳ")
public class SemesterController {

    private final SemesterService semesterService;

    @GetMapping
    @Operation(summary = "Lấy danh sách học kỳ")
    public ResponseEntity<ResponseData<List<SemesterResponse>>> getAll() {
        return ResponseEntity.ok(
                ResponseData.success("Lấy danh sách học kỳ thành công", semesterService.getAll(), 200)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy chi tiết học kỳ")
    public ResponseEntity<ResponseData<SemesterResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                ResponseData.success("Lấy chi tiết học kỳ thành công", semesterService.getById(id), 200)
        );
    }

    @PostMapping
    @Operation(summary = "Tạo học kỳ")
    public ResponseEntity<ResponseData<SemesterResponse>> create(@Valid @RequestBody SemesterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseData.success("Tạo học kỳ thành công", semesterService.create(request), 201));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật học kỳ")
    public ResponseEntity<ResponseData<SemesterResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody SemesterRequest request) {
        return ResponseEntity.ok(
                ResponseData.success("Cập nhật học kỳ thành công", semesterService.update(id, request), 200)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa mềm học kỳ")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Integer id) {
        semesterService.delete(id);
        return ResponseEntity.ok(ResponseData.success("Xóa học kỳ thành công", null, 200));
    }
}
