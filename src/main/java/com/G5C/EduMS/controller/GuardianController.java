package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.GuardianCreateRequest;
import com.G5C.EduMS.dto.request.GuardianUpdateRequest;
import com.G5C.EduMS.dto.reponse.ResponseData;
import com.G5C.EduMS.dto.reponse.GuardianResponse;
import com.G5C.EduMS.service.GuardianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/guardians")
@RequiredArgsConstructor
@Tag(name = "Guardian Management", description = "Các API quản lý thông tin phụ huynh")
public class GuardianController {

    private final GuardianService guardianService;

    @Operation(summary = "Lấy danh sách phụ huynh", description = "Hỗ trợ phân trang và tìm kiếm theo tên hoặc số điện thoại")
    @GetMapping
    public ResponseEntity<ResponseData> getAllGuardians(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {

        Page<GuardianResponse> guardians = guardianService.getAllGuardians(page, size, keyword);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Lấy danh sách phụ huynh thành công", guardians));
    }

    @Operation(summary = "Lấy chi tiết 1 phụ huynh", description = "Trả về thông tin phụ huynh kèm theo danh sách các con đang theo học")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getGuardianById(@PathVariable Integer id) {

        GuardianResponse guardian = guardianService.getGuardianById(id);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Lấy thông tin phụ huynh thành công", guardian));
    }

    @Operation(summary = "Thêm mới phụ huynh", description = "Tạo mới một hồ sơ phụ huynh (chưa bao gồm logic gán học sinh)")
    @PostMapping
    public ResponseEntity<ResponseData> createGuardian(@Valid @RequestBody GuardianCreateRequest request) {

        GuardianResponse createdGuardian = guardianService.createGuardian(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseData(201, "CREATED", "Thêm mới phụ huynh thành công", createdGuardian));
    }

    @Operation(summary = "Cập nhật hồ sơ phụ huynh", description = "Cập nhật các thông tin liên hệ của phụ huynh theo ID")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updateGuardian(
            @PathVariable Integer id,
            @Valid @RequestBody GuardianUpdateRequest request) {

        GuardianResponse updatedGuardian = guardianService.updateGuardian(id, request);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Cập nhật thông tin phụ huynh thành công", updatedGuardian));
    }

    @Operation(summary = "Xóa mềm phụ huynh", description = "Đánh dấu deleted = true để ẩn phụ huynh khỏi hệ thống")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData> deleteGuardian(@PathVariable Integer id) {

        guardianService.deleteGuardian(id);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Xóa phụ huynh thành công", null));
    }
}