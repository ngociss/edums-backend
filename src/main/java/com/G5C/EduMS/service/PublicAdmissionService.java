package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.ApplicationSubmitRequest;
import com.G5C.EduMS.dto.response.PublicLookupResponse;
import com.G5C.EduMS.dto.response.SelectionOptionsResponse;

import java.util.List;
import java.util.Map;

public interface PublicAdmissionService {

    // 1. Lấy danh sách đợt đang mở
    List<Map<String, Object>> getActivePeriods();

    // 2. Lấy Ngành dựa trên cấu hình Benchmark của đợt đó
    List<Map<String, Object>> getAvailableMajors(Integer periodId);

    // 3. Lấy Khối dựa trên cấu hình Benchmark của Đợt + Ngành
    List<Map<String, Object>> getAvailableBlocks(Integer periodId, Integer majorId);

    void submitApplication(ApplicationSubmitRequest request);

    // Trả về List để xử lý trường hợp nộp nhiều đợt
    List<PublicLookupResponse> lookupApplication(String nationalId, String phone);

}