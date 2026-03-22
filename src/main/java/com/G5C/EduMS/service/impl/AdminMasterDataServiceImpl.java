package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.*;
import com.G5C.EduMS.dto.response.*;
import com.G5C.EduMS.mapper.AdminMasterDataMapper;
import com.G5C.EduMS.model.AdmissionBlock;
import com.G5C.EduMS.model.AdmissionPeriod;
import com.G5C.EduMS.model.BenchmarkScore;
import com.G5C.EduMS.model.Major;
import com.G5C.EduMS.repository.*;
import com.G5C.EduMS.service.AdminMasterDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminMasterDataServiceImpl implements AdminMasterDataService {

    private final AdmissionPeriodRepository periodRepository;
    private final AdmissionBlockRepository blockRepository;
    private final BenchmarkScoreRepository benchmarkRepository;
    private final MajorRepository majorRepository;
    private final AdmissionApplicationRepository applicationRepository;

    // Inject Mapper do MapStruct tự động generate
    private final AdminMasterDataMapper mapper;

    // =========================================================================
    // 1. QUẢN LÝ ĐỢT TUYỂN SINH
    // =========================================================================

    @Override
    public PageResponse<PeriodAdminResponse> getPeriods(BaseFilterRequest filter) {
        Pageable pageable = createPageable(filter);
        Page<AdmissionPeriod> pageData = periodRepository.findAll(pageable);

        // Ánh xạ bằng Mapper
        List<PeriodAdminResponse> dtoList = pageData.getContent().stream()
                .map(mapper::toPeriodResponse)
                .collect(Collectors.toList());

        return toPageResponse(pageData, dtoList);
    }

    @Override
    public PeriodAdminResponse getPeriodById(Integer id) {
        AdmissionPeriod period = periodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy Đợt tuyển sinh với ID: " + id));
        return mapper.toPeriodResponse(period);
    }

    @Override
    @Transactional
    public PeriodAdminResponse createPeriod(PeriodRequest request) {
        validatePeriodTime(request);

        // Map từ Request sang Entity
        AdmissionPeriod period = mapper.toPeriodEntity(request);
        return mapper.toPeriodResponse(periodRepository.save(period));
    }

    @Override
    @Transactional
    public PeriodAdminResponse updatePeriod(Integer id, PeriodRequest request) {
        validatePeriodTime(request);

        AdmissionPeriod period = periodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy Đợt tuyển sinh"));

        // Dùng Mapper để cập nhật dữ liệu trực tiếp vào Entity hiện tại
        mapper.updatePeriodFromRequest(request, period);

        return mapper.toPeriodResponse(periodRepository.save(period));
    }

    @Override
    @Transactional
    public void deletePeriod(Integer id) {
        AdmissionPeriod period = periodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy Đợt tuyển sinh"));

        boolean hasApplications = applicationRepository.existsByAdmissionPeriodIdAndDeletedFalse(id);
        if (hasApplications) {
            throw new IllegalStateException("Không thể xóa đợt tuyển sinh đã có hồ sơ đăng ký!");
        }

        period.setDeleted(true);
        periodRepository.save(period);
    }

    // =========================================================================
    // 2. QUẢN LÝ KHỐI XÉT TUYỂN
    // =========================================================================

    @Override
    public List<AdmissionBlock> getAllBlocks() {
        return blockRepository.findAllByDeletedFalse();
    }

    @Override
    @Transactional
    public AdmissionBlock createBlock(BlockRequest request) {
        AdmissionBlock block = mapper.toBlockEntity(request);
        return blockRepository.save(block);
    }

    @Override
    @Transactional
    public AdmissionBlock updateBlock(Integer id, BlockRequest request) {
        AdmissionBlock block = blockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy Khối xét tuyển"));

        mapper.updateBlockFromRequest(request, block);
        return blockRepository.save(block);
    }

    @Override
    @Transactional
    public void deleteBlock(Integer id) {
        AdmissionBlock block = blockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy Khối xét tuyển"));
        block.setDeleted(true);
        blockRepository.save(block);
    }

    // =========================================================================
    // 3. THIẾT LẬP ĐIỂM CHUẨN
    // =========================================================================

    @Override
    public PageResponse<BenchmarkResponse> getBenchmarks(BenchmarkFilterRequest filter) {
        Pageable pageable = createPageable(filter);
        Page<BenchmarkScore> pageData = benchmarkRepository.findAll(pageable);

        List<BenchmarkResponse> dtoList = pageData.getContent().stream()
                .map(mapper::toBenchmarkResponse)
                .collect(Collectors.toList());

        return toPageResponse(pageData, dtoList);
    }

    @Override
    @Transactional
    public void saveBulkBenchmarks(BulkBenchmarkRequest request) {
        AdmissionPeriod period = periodRepository.findById(request.getPeriodId())
                .orElseThrow(() -> new IllegalArgumentException("Đợt tuyển sinh không tồn tại"));

        for (BulkBenchmarkRequest.BenchmarkItem item : request.getBenchmarks()) {
            Major major = majorRepository.findById(item.getMajorId())
                    .orElseThrow(() -> new IllegalArgumentException("Ngành học không tồn tại"));
            AdmissionBlock block = blockRepository.findById(item.getBlockId())
                    .orElseThrow(() -> new IllegalArgumentException("Khối xét tuyển không tồn tại"));

            // Tìm xem đã có điểm chuẩn cho tổ hợp này chưa (Update nếu có, Create nếu chưa)
            BenchmarkScore benchmark = benchmarkRepository
                    .findByMajorIdAndAdmissionBlockIdAndAdmissionPeriodIdAndDeletedFalse(
                            major.getId(), block.getId(), period.getId())
                    .orElse(BenchmarkScore.builder()
                            .major(major)
                            .admissionBlock(block)
                            .admissionPeriod(period)
                            .deleted(false)
                            .build());

            benchmark.setScore(item.getScore());
            benchmarkRepository.save(benchmark);
        }
    }

    @Override
    @Transactional
    public BenchmarkResponse updateBenchmark(Integer id, BenchmarkRequest request) {
        BenchmarkScore benchmark = benchmarkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Điểm chuẩn không tồn tại"));
        benchmark.setScore(request.getScore());

        return mapper.toBenchmarkResponse(benchmarkRepository.save(benchmark));
    }

    @Override
    @Transactional
    public void deleteBenchmark(Integer id) {
        BenchmarkScore benchmark = benchmarkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Điểm chuẩn không tồn tại"));
        benchmark.setDeleted(true);
        benchmarkRepository.save(benchmark);
    }

    // =========================================================================
    // 4. API PHỤ TRỢ (DROPDOWN OPTIONS)
    // =========================================================================

    @Override
    public SelectionOptionsResponse getSelectionOptions() {
        // Lấy danh sách Ngành
        List<Map<String, Object>> majors = majorRepository.findAllByDeletedFalse().stream()
                .map(m -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", m.getId());
                    map.put("majorName", m.getMajorName());
                    map.put("majorCode", m.getMajorCode());
                    return map;
                }).collect(Collectors.toList());

        // Lấy danh sách Khối
        List<Map<String, Object>> blocks = blockRepository.findAllByDeletedFalse().stream()
                .map(b -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", b.getId());
                    map.put("blockName", b.getBlockName());
                    return map;
                }).collect(Collectors.toList());

        // Lấy danh sách Đợt
        List<Map<String, Object>> periods = periodRepository.findAll().stream()
                .filter(p -> !p.isDeleted())
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", p.getId());
                    map.put("periodName", p.getPeriodName());
                    return map;
                }).collect(Collectors.toList());

        return SelectionOptionsResponse.builder()
                .majors(majors)
                .blocks(blocks)
                .periods(periods)
                .build();
    }

    // =========================================================================
    // HELPER METHODS (HÀM BỔ TRỢ)
    // =========================================================================

    private void validatePeriodTime(PeriodRequest request) {
        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new IllegalArgumentException("Thời gian bắt đầu không được lớn hơn thời gian kết thúc");
        }
    }

    private Pageable createPageable(BaseFilterRequest filter) {
        Sort sort = Sort.by(Sort.Direction.fromString(filter.getSortDirection()), filter.getSortBy());
        return PageRequest.of(filter.getPage(), filter.getSize(), sort);
    }

    private <T> PageResponse<T> toPageResponse(Page<?> springPage, List<T> data) {
        return PageResponse.<T>builder()
                .page(springPage.getNumber())
                .size(springPage.getSize())
                .totalElements(springPage.getTotalElements())
                .totalPages(springPage.getTotalPages())
                .data(data)
                .build();
    }
}