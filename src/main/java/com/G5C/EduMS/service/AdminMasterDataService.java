package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.*;
import com.G5C.EduMS.dto.response.*;
import com.G5C.EduMS.model.AdmissionBlock;

import java.util.List;

public interface AdminMasterDataService {

    // =========================================================================
    // 1. QUẢN LÝ ĐỢT TUYỂN SINH (ADMISSION PERIODS)
    // =========================================================================

    PageResponse<AdmissionPeriodAdminResponse> getPeriods(BaseFilterRequest filter);

    AdmissionPeriodAdminResponse getPeriodById(Integer id);

    AdmissionPeriodAdminResponse createPeriod(AdmissionPeriodRequest request);

    AdmissionPeriodAdminResponse updatePeriod(Integer id, AdmissionPeriodRequest request);

    void deletePeriod(Integer id);

    void createFullAdmissionCampaign(CreateAdmissionCampaignRequest request);

    // =========================================================================
    // 2. QUẢN LÝ KHỐI XÉT TUYỂN (ADMISSION BLOCKS)
    // =========================================================================

    List<AdmissionBlockResponse> getAllBlocks();

    AdmissionBlockResponse createBlock(BlockRequest request);

    AdmissionBlockResponse updateBlock(Integer id, BlockRequest request);

    void deleteBlock(Integer id);

    // =========================================================================
    // 3. THIẾT LẬP ĐIỂM CHUẨN (BENCHMARK SCORES)
    // =========================================================================

    PageResponse<BenchmarkResponse> getBenchmarks(BenchmarkFilterRequest filter);

    void saveBulkBenchmarks(BulkBenchmarkRequest request);

    BenchmarkResponse updateBenchmark(Integer id, BenchmarkRequest request);

    void deleteBenchmark(Integer id);

    // =========================================================================
    // 4. API PHỤ TRỢ (FORM OPTIONS)
    // =========================================================================

    SelectionOptionsResponse getSelectionOptions();

}