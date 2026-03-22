package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.*;
import com.G5C.EduMS.dto.response.BenchmarkResponse;
import com.G5C.EduMS.dto.response.PageResponse;
import com.G5C.EduMS.dto.response.PeriodAdminResponse;
import com.G5C.EduMS.dto.response.SelectionOptionsResponse;
import com.G5C.EduMS.model.AdmissionBlock;

import java.util.List;

public interface AdminMasterDataService {

    // =========================================================================
    // 1. QUẢN LÝ ĐỢT TUYỂN SINH (ADMISSION PERIODS)
    // =========================================================================

    PageResponse<PeriodAdminResponse> getPeriods(BaseFilterRequest filter);

    PeriodAdminResponse getPeriodById(Integer id);

    PeriodAdminResponse createPeriod(PeriodRequest request);

    PeriodAdminResponse updatePeriod(Integer id, PeriodRequest request);

    void deletePeriod(Integer id);

    // =========================================================================
    // 2. QUẢN LÝ KHỐI XÉT TUYỂN (ADMISSION BLOCKS)
    // =========================================================================

    List<AdmissionBlock> getAllBlocks();

    AdmissionBlock createBlock(BlockRequest request);

    AdmissionBlock updateBlock(Integer id, BlockRequest request);

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