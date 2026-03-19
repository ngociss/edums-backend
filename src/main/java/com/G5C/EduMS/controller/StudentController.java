package com.G5C.EduMS.controller;

import com.G5C.EduMS.common.enums.StudentStatus;
import com.G5C.EduMS.dto.request.StudentCreateRequest;
import com.G5C.EduMS.dto.request.StudentStatusUpdateRequest;
import com.G5C.EduMS.dto.request.StudentUpdateRequest;
import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.dto.response.StudentResponse;
import com.G5C.EduMS.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "Student Management", description = "Các API quản lý thông tin sinh viên")
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "Lấy danh sách sinh viên", description = "Hỗ trợ phân trang và lọc theo từ khóa, lớp, ngành, trạng thái")
    @GetMapping
    public ResponseEntity<ResponseData> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer classId,
            @RequestParam(required = false) Integer majorId,
            @RequestParam(required = false) StudentStatus status) {

        Page<StudentResponse> students = studentService.getAllStudents(page, size, keyword, classId, majorId, status);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Lấy danh sách sinh viên thành công", students));
    }

    @Operation(summary = "Lấy chi tiết 1 sinh viên", description = "Truyền vào ID của sinh viên")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getStudentById(@PathVariable Integer id) {
        StudentResponse student = studentService.getStudentById(id);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Lấy thông tin sinh viên thành công", student));
    }

    @Operation(summary = "Thêm mới sinh viên", description = "Tạo mới một hồ sơ sinh viên")
    @PostMapping
    public ResponseEntity<ResponseData> createStudent(@Valid @RequestBody StudentCreateRequest request) {
        StudentResponse createdStudent = studentService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseData(201, "CREATED", "Thêm mới sinh viên thành công", createdStudent));
    }

    @Operation(summary = "Cập nhật hồ sơ sinh viên", description = "Cập nhật toàn bộ thông tin của sinh viên theo ID")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updateStudent(
            @PathVariable Integer id,
            @Valid @RequestBody StudentUpdateRequest request) {

        StudentResponse updatedStudent = studentService.updateStudent(id, request);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Cập nhật thông tin sinh viên thành công", updatedStudent));
    }

    @Operation(summary = "Cập nhật trạng thái sinh viên", description = "Dùng để đình chỉ, bảo lưu hoặc kích hoạt lại sinh viên")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseData> updateStudentStatus(
            @PathVariable Integer id,
            @Valid @RequestBody StudentStatusUpdateRequest request) {

        studentService.updateStudentStatus(id, request);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Cập nhật trạng thái thành công", null));
    }

    @Operation(summary = "Xóa mềm sinh viên", description = "Đánh dấu deleted = true thay vì xóa khỏi database")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData> deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Xóa sinh viên thành công", null));
    }
}