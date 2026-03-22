package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.ApplicationFilterRequest;
import com.G5C.EduMS.dto.request.ApplicationReviewRequest;
import com.G5C.EduMS.dto.request.BulkReviewRequest;
import com.G5C.EduMS.dto.request.OnboardingRequest;
import com.G5C.EduMS.dto.response.ApplicationAdminResponse;
import com.G5C.EduMS.dto.response.PageResponse;
import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.service.AdminApplicationService;
import com.G5C.EduMS.service.AdmissionOnboardingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/admissions/applications")
@RequiredArgsConstructor
public class AdminApplicationController {

    private final AdminApplicationService applicationService;
    private final AdmissionOnboardingService onboardingService;

    // =========================================================================
    // 1. LẤY DANH SÁCH & CHI TIẾT HỒ SƠ
    // =========================================================================

    @GetMapping
    public ResponseEntity<ResponseData<PageResponse<ApplicationAdminResponse>>> getApplications(
            @ModelAttribute ApplicationFilterRequest filter) {

        PageResponse<ApplicationAdminResponse> result = applicationService.getApplications(filter);
        return ResponseEntity.ok(ResponseData.success("Lấy danh sách hồ sơ thành công", result, HttpStatus.OK.value()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<ApplicationAdminResponse>> getApplicationById(@PathVariable Integer id) {

        ApplicationAdminResponse result = applicationService.getApplicationById(id);
        return ResponseEntity.ok(ResponseData.success("Lấy chi tiết hồ sơ thành công", result, HttpStatus.OK.value()));
    }

    // =========================================================================
    // 2. XÉT DUYỆT HỒ SƠ (THỦ CÔNG)
    // =========================================================================

    @PatchMapping("/{id}/review")
    public ResponseEntity<ResponseData<Void>> reviewSingleApplication(
            @PathVariable Integer id,
            @Valid @RequestBody ApplicationReviewRequest request) {

        applicationService.reviewSingleApplication(id, request);
        return ResponseEntity.ok(ResponseData.success("Đã cập nhật trạng thái hồ sơ thành công", null, HttpStatus.OK.value()));
    }

    @PostMapping("/bulk-review")
    public ResponseEntity<ResponseData<Void>> reviewBulkApplications(
            @Valid @RequestBody BulkReviewRequest request) {

        applicationService.reviewBulkApplications(request);
        return ResponseEntity.ok(ResponseData.success("Xét duyệt hồ sơ hàng loạt thành công", null, HttpStatus.OK.value()));
    }

// =========================================================================
    // 3. TỰ ĐỘNG HÓA (AUTO-SCREENING & ONBOARDING) - VỪA BỔ SUNG
    // =========================================================================

    /**
     * API kích hoạt hệ thống tự quét điểm của toàn bộ hồ sơ PENDING trong một đợt
     */
    @PostMapping("/auto-screen/{periodId}")
    public ResponseEntity<ResponseData<Void>> autoScreenApplications(@PathVariable Integer periodId) {

        onboardingService.autoScreenApplications(periodId);
        return ResponseEntity.ok(ResponseData.success("Hệ thống đã quét điểm và xét duyệt tự động thành công", null, HttpStatus.OK.value()));
    }

    /**
     * API chốt danh sách nhập học: Sinh tài khoản và hồ sơ Sinh viên
     */
    @PostMapping("/onboard")
    public ResponseEntity<ResponseData<Void>> processOnboarding(
            @Valid @RequestBody OnboardingRequest request) {

        onboardingService.processOnboarding(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseData.success("Chốt danh sách và tạo tài khoản sinh viên thành công", null, HttpStatus.CREATED.value()));
    }
}