package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.ApplicationSubmitRequest;
import com.G5C.EduMS.dto.response.PublicLookupResponse;
import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.dto.response.SelectionOptionsResponse;
import com.G5C.EduMS.service.PublicAdmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/public/admissions")
@RequiredArgsConstructor
public class PublicAdmissionController {

    private final PublicAdmissionService publicAdmissionService;

    // Lấy danh sách các Đợt đang mở
    @GetMapping("/active-periods")
    public ResponseEntity<ResponseData<List<Map<String, Object>>>> getActivePeriods() {
        List<Map<String, Object>> result = publicAdmissionService.getActivePeriods();
        return ResponseEntity
                .ok(ResponseData.success("Lấy danh sách đợt tuyển sinh thành công", result, HttpStatus.OK.value()));
    }

    // Lấy Ngành đang mở TRONG 1 Đợt cụ thể (Dựa vào cấu hình điểm chuẩn)
    @GetMapping("/periods/{periodId}/majors")
    public ResponseEntity<ResponseData<List<Map<String, Object>>>> getAvailableMajors(@PathVariable Integer periodId) {
        List<Map<String, Object>> result = publicAdmissionService.getAvailableMajors(periodId);
        return ResponseEntity.ok(ResponseData.success("Lấy danh sách ngành thành công", result, HttpStatus.OK.value()));
    }

    // Lấy Khối đang xét TRONG 1 Ngành của 1 Đợt cụ thể
    @GetMapping("/periods/{periodId}/majors/{majorId}/blocks")
    public ResponseEntity<ResponseData<List<Map<String, Object>>>> getAvailableBlocks(
            @PathVariable Integer periodId,
            @PathVariable Integer majorId) {
        List<Map<String, Object>> result = publicAdmissionService.getAvailableBlocks(periodId, majorId);
        return ResponseEntity
                .ok(ResponseData.success("Lấy danh sách khối xét tuyển thành công", result, HttpStatus.OK.value()));
    }

    @PostMapping("/apply")
    public ResponseEntity<ResponseData<Void>> submitApplication(@Valid @RequestBody ApplicationSubmitRequest request) {
        publicAdmissionService.submitApplication(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseData.success("Nộp hồ sơ thành công! Vui lòng lưu lại CCCD và SĐT để tra cứu.", null,
                        HttpStatus.CREATED.value()));
    }

    // Đổi về trả về List để phòng trường hợp thí sinh nộp nhiều đợt (nhiều năm)
    @GetMapping("/lookup")
    public ResponseEntity<ResponseData<List<PublicLookupResponse>>> lookupApplication(
            @RequestParam String nationalId,
            @RequestParam String phone) {
        List<PublicLookupResponse> result = publicAdmissionService.lookupApplication(nationalId, phone);
        return ResponseEntity.ok(ResponseData.success("Tra cứu hồ sơ thành công", result, HttpStatus.OK.value()));
    }
}