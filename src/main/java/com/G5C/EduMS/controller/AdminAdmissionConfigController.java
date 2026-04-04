package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.*;
import com.G5C.EduMS.dto.response.*;
import com.G5C.EduMS.model.AdmissionBlock;
import com.G5C.EduMS.service.AdminMasterDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/admissions/config")
@RequiredArgsConstructor
public class AdminAdmissionConfigController {

    private final AdminMasterDataService masterDataService;

    // =========================================================================
    // 1. QUẢN LÝ ĐỢT TUYỂN SINH (ADMISSION PERIODS)
    // =========================================================================

    @GetMapping("/periods")
        public ResponseEntity<ResponseData<PageResponse<AdmissionPeriodAdminResponse>>> getPeriods(@ModelAttribute BaseFilterRequest filter) {
        PageResponse<AdmissionPeriodAdminResponse> result = masterDataService.getPeriods(filter);
        return ResponseEntity.ok(ResponseData.success("Lấy danh sách đợt tuyển sinh thành công", result, HttpStatus.OK.value()));
    }

    @GetMapping("/periods/{id}")
    public ResponseEntity<ResponseData<AdmissionPeriodAdminResponse>> getPeriodById(@PathVariable Integer id) {
        AdmissionPeriodAdminResponse result = masterDataService.getPeriodById(id);
        return ResponseEntity.ok(ResponseData.success("Lấy thông tin đợt tuyển sinh thành công", result, HttpStatus.OK.value()));
    }

    @PutMapping("/periods/{id}")
    public ResponseEntity<ResponseData<AdmissionPeriodAdminResponse>> updatePeriod(
            @PathVariable Integer id,
            @Valid @RequestBody AdmissionPeriodRequest request) {
        AdmissionPeriodAdminResponse result = masterDataService.updatePeriod(id, request);
        return ResponseEntity.ok(ResponseData.success("Cập nhật đợt tuyển sinh thành công", result, HttpStatus.OK.value()));
    }

    @DeleteMapping("/periods/{id}")
    public ResponseEntity<ResponseData<Void>> deletePeriod(@PathVariable Integer id) {
        masterDataService.deletePeriod(id);
        return ResponseEntity.ok(ResponseData.success("Xóa đợt tuyển sinh thành công", null, HttpStatus.OK.value()));
    }

    @PostMapping("/periods/campaign")
    public ResponseEntity<ResponseData<Void>> createCampaign(@Valid @RequestBody CreateAdmissionCampaignRequest request) {
        // Gọi service xử lý logic all-in-one
        masterDataService.createFullAdmissionCampaign(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseData.success("Tạo đợt tuyển sinh mới thành công", null, HttpStatus.CREATED.value()));
    }


    // =========================================================================
    // 2. QUẢN LÝ KHỐI XÉT TUYỂN (ADMISSION BLOCKS)
    // =========================================================================

    @GetMapping("/blocks")
    public ResponseEntity<ResponseData<List<AdmissionBlockResponse>>> getAllBlocks() {
        List<AdmissionBlockResponse> result = masterDataService.getAllBlocks();
        return ResponseEntity.ok(ResponseData.success("Lấy danh sách khối xét tuyển thành công", result, HttpStatus.OK.value()));
    }

    @PostMapping("/blocks")
    public ResponseEntity<ResponseData<AdmissionBlockResponse>> createBlock(@Valid @RequestBody BlockRequest request) {
        AdmissionBlockResponse result = masterDataService.createBlock(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseData.success("Tạo khối xét tuyển thành công", result, HttpStatus.CREATED.value()));
    }

    @PutMapping("/blocks/{id}")
    public ResponseEntity<ResponseData<AdmissionBlockResponse>> updateBlock(
            @PathVariable Integer id,
            @Valid @RequestBody BlockRequest request) {
        AdmissionBlockResponse result = masterDataService.updateBlock(id, request);
        return ResponseEntity.ok(ResponseData.success("Cập nhật khối xét tuyển thành công", result, HttpStatus.OK.value()));
    }

    @DeleteMapping("/blocks/{id}")
    public ResponseEntity<ResponseData<Void>> deleteBlock(@PathVariable Integer id) {
        masterDataService.deleteBlock(id);
        return ResponseEntity.ok(ResponseData.success("Xóa khối xét tuyển thành công", null, HttpStatus.OK.value()));
    }

    // =========================================================================
    // 3. THIẾT LẬP ĐIỂM CHUẨN (BENCHMARK SCORES)
    // =========================================================================

    @GetMapping("/benchmarks")
    public ResponseEntity<ResponseData<PageResponse<BenchmarkResponse>>> getBenchmarks(@ModelAttribute BenchmarkFilterRequest filter) {
        PageResponse<BenchmarkResponse> result = masterDataService.getBenchmarks(filter);
        return ResponseEntity.ok(ResponseData.success("Lấy danh sách điểm chuẩn thành công", result, HttpStatus.OK.value()));
    }

    @PostMapping("/benchmarks/bulk")
    public ResponseEntity<ResponseData<Void>> saveBulkBenchmarks(@Valid @RequestBody BulkBenchmarkRequest request) {
        masterDataService.saveBulkBenchmarks(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseData.success("Lưu điểm chuẩn hàng loạt thành công", null, HttpStatus.CREATED.value()));
    }

    @PutMapping("/benchmarks/{id}")
    public ResponseEntity<ResponseData<BenchmarkResponse>> updateSingleBenchmark(
            @PathVariable Integer id,
            @Valid @RequestBody BenchmarkRequest request) {
        BenchmarkResponse result = masterDataService.updateBenchmark(id, request);
        return ResponseEntity.ok(ResponseData.success("Cập nhật điểm chuẩn thành công", result, HttpStatus.OK.value()));
    }

    @DeleteMapping("/benchmarks/{id}")
    public ResponseEntity<ResponseData<Void>> deleteBenchmark(@PathVariable Integer id) {
        masterDataService.deleteBenchmark(id);
        return ResponseEntity.ok(ResponseData.success("Xóa điểm chuẩn thành công", null, HttpStatus.OK.value()));
    }

    // =========================================================================
    // lấy dữ liệu cấu hình
    // =========================================================================
    @GetMapping("/form-options")
    public ResponseEntity<ResponseData<SelectionOptionsResponse>> getOptionsForSetup() {
        SelectionOptionsResponse result = masterDataService.getSelectionOptions();
        return ResponseEntity.ok(ResponseData.success("Lấy dữ liệu cấu hình thành công", result, HttpStatus.OK.value()));
    }
}