package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.LecturerCreateRequest;
import com.G5C.EduMS.dto.request.LecturerUpdateRequest;
import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.dto.response.LecturerResponse;
import com.G5C.EduMS.service.LecturerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lecturers")
@RequiredArgsConstructor
@Tag(name = "Lecturer Management", description = "Các API quản lý thông tin giảng viên")
public class LecturerController {

    private final LecturerService lecturerService;

    @Operation(summary = "Lấy danh sách giảng viên", description = "Hỗ trợ phân trang và tìm kiếm theo tên hoặc email")
    @GetMapping
    public ResponseEntity<ResponseData> getAllLecturers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {

        Page<LecturerResponse> lecturers = lecturerService.getAllLecturers(page, size, keyword);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Lấy danh sách giảng viên thành công", lecturers));
    }

    @Operation(summary = "Lấy chi tiết 1 giảng viên", description = "Truyền vào ID của giảng viên để xem chi tiết")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getLecturerById(@PathVariable Integer id) {

        LecturerResponse lecturer = lecturerService.getLecturerById(id);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Lấy thông tin giảng viên thành công", lecturer));
    }

    @Operation(summary = "Thêm mới giảng viên", description = "Tạo mới một hồ sơ giảng viên vào hệ thống")
    @PostMapping
    public ResponseEntity<ResponseData> createLecturer(@Valid @RequestBody LecturerCreateRequest request) {

        LecturerResponse createdLecturer = lecturerService.createLecturer(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseData(201, "CREATED", "Thêm mới giảng viên thành công", createdLecturer));
    }

    @Operation(summary = "Cập nhật hồ sơ giảng viên", description = "Cập nhật các thông tin của giảng viên theo ID")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updateLecturer(
            @PathVariable Integer id,
            @Valid @RequestBody LecturerUpdateRequest request) {

        LecturerResponse updatedLecturer = lecturerService.updateLecturer(id, request);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Cập nhật thông tin giảng viên thành công", updatedLecturer));
    }

    @Operation(summary = "Xóa mềm giảng viên", description = "Đánh dấu deleted = true để ẩn giảng viên khỏi hệ thống thay vì xóa cứng")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData> deleteLecturer(@PathVariable Integer id) {

        lecturerService.deleteLecturer(id);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Xóa giảng viên thành công", null));
    }
}