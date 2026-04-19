package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.LecturerCreateRequest;
import com.G5C.EduMS.dto.request.LecturerUpdateRequest;
import com.G5C.EduMS.dto.response.LecturerCourseSectionResponse;
import com.G5C.EduMS.dto.response.LecturerResponse;
import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.service.LecturerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lecturers")
@RequiredArgsConstructor
@Tag(name = "Lecturer Management", description = "Cac API quan ly thong tin giang vien")
public class LecturerController {

    private final LecturerService lecturerService;

    @Operation(summary = "Lay danh sach giang vien", description = "Ho tro phan trang va tim kiem theo ten hoac email")
    @GetMapping
    public ResponseEntity<ResponseData> getAllLecturers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {

        Page<LecturerResponse> lecturers = lecturerService.getAllLecturers(page, size, keyword);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Lay danh sach giang vien thanh cong", lecturers));
    }

    @Operation(summary = "Lay chi tiet 1 giang vien", description = "Truyen vao ID cua giang vien de xem chi tiet")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getLecturerById(@PathVariable Integer id) {

        LecturerResponse lecturer = lecturerService.getLecturerById(id);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Lay thong tin giang vien thanh cong", lecturer));
    }

    @Operation(summary = "Lay danh sach lop hoc phan cua giang vien hien tai")
    @GetMapping("/me/course-sections")
    public ResponseEntity<ResponseData<List<LecturerCourseSectionResponse>>> getMyCourseSections(
            Authentication authentication) {
        return ResponseEntity.ok(
                ResponseData.success(
                        "Lay danh sach lop hoc phan cua giang vien thanh cong",
                        lecturerService.getMyCourseSections(authentication.getName()),
                        200
                )
        );
    }

    @Operation(summary = "Them moi giang vien", description = "Tao moi mot ho so giang vien vao he thong")
    @PostMapping
    public ResponseEntity<ResponseData> createLecturer(@Valid @RequestBody LecturerCreateRequest request) {

        LecturerResponse createdLecturer = lecturerService.createLecturer(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseData(201, "CREATED", "Them moi giang vien thanh cong", createdLecturer));
    }

    @Operation(summary = "Cap nhat ho so giang vien", description = "Cap nhat cac thong tin cua giang vien theo ID")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> updateLecturer(
            @PathVariable Integer id,
            @Valid @RequestBody LecturerUpdateRequest request) {

        LecturerResponse updatedLecturer = lecturerService.updateLecturer(id, request);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Cap nhat thong tin giang vien thanh cong", updatedLecturer));
    }

    @Operation(summary = "Xoa mem giang vien", description = "Danh dau deleted = true de an giang vien khoi he thong thay vi xoa cung")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData> deleteLecturer(@PathVariable Integer id) {

        lecturerService.deleteLecturer(id);
        return ResponseEntity.ok(new ResponseData(200, "SUCCESS", "Xoa giang vien thanh cong", null));
    }
}
