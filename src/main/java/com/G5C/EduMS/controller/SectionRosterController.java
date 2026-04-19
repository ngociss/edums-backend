package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.dto.response.SectionRosterResponse;
import com.G5C.EduMS.dto.response.SessionAttendanceRosterResponse;
import com.G5C.EduMS.service.SectionRosterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Section Roster", description = "APIs for official section rosters and attendance roster")
public class SectionRosterController {

    private final SectionRosterService sectionRosterService;

    @GetMapping("/api/v1/course-sections/{sectionId}/roster")
    @Operation(summary = "Get official roster by course section")
    public ResponseEntity<ResponseData<List<SectionRosterResponse>>> getBySection(
            Authentication authentication,
            @PathVariable Integer sectionId) {
        return ResponseEntity.ok(
                ResponseData.success("Section roster loaded successfully",
                        sectionRosterService.getBySection(authentication.getName(), sectionId),
                        200)
        );
    }

    @PostMapping("/api/v1/course-sections/{sectionId}/roster/sync")
    @Operation(summary = "Sync official roster from confirmed registrations")
    public ResponseEntity<ResponseData<List<SectionRosterResponse>>> syncSectionRoster(
            Authentication authentication,
            @PathVariable Integer sectionId) {
        return ResponseEntity.ok(
                ResponseData.success("Section roster synchronized successfully",
                        sectionRosterService.syncSectionRoster(authentication.getName(), sectionId),
                        200)
        );
    }

    @GetMapping("/api/v1/class-sessions/{sessionId}/attendance-roster")
    @Operation(summary = "Get attendance roster for a class session")
    public ResponseEntity<ResponseData<List<SessionAttendanceRosterResponse>>> getAttendanceRosterBySession(
            Authentication authentication,
            @PathVariable Integer sessionId) {
        return ResponseEntity.ok(
                ResponseData.success("Attendance roster loaded successfully",
                        sectionRosterService.getAttendanceRosterBySession(authentication.getName(), sessionId),
                        200)
        );
    }
}
