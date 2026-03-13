package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.reponse.LecturerResponse;
import com.G5C.EduMS.dto.reponse.ResponseData;
import com.G5C.EduMS.dto.reponse.StudentResponse;
import com.G5C.EduMS.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "APIs for viewing personal information")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/students/{id}")
    @Operation(summary = "Get student profile by ID")
    public ResponseEntity<ResponseData<StudentResponse>> getStudent(
            @PathVariable Integer id) {
        return ResponseEntity.ok(
            ResponseData.success("Success", profileService.getStudentById(id), 200));
    }

    @GetMapping("/lecturers/{id}")
    @Operation(summary = "Get lecturer profile by ID")
    public ResponseEntity<ResponseData<LecturerResponse>> getLecturer(
            @PathVariable Integer id) {
        return ResponseEntity.ok(
            ResponseData.success("Success", profileService.getLecturerById(id), 200));
    }
}
