package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.AdmissionPeriodStatus;
import com.G5C.EduMS.dto.request.*;
import com.G5C.EduMS.dto.response.*;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.AdminMasterDataMapper;
import com.G5C.EduMS.model.AdmissionBlock;
import com.G5C.EduMS.model.AdmissionPeriod;
import com.G5C.EduMS.model.BenchmarkScore;
import com.G5C.EduMS.model.Major;
import com.G5C.EduMS.repository.*;
import com.G5C.EduMS.service.AdminMasterDataService;
import com.G5C.EduMS.validator.AdmissionCampaignValidator;
import com.G5C.EduMS.validator.AdmissionPeriodValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    private final AdmissionPeriodValidator admissionPeriodValidator;
    private final AdmissionCampaignValidator admissionCampaignValidator;
    private final AdminMasterDataMapper mapper;

    // =========================================================================
    // 1. QUẢN LÝ ĐỢT TUYỂN SINH
    // =========================================================================

    @Override
    public PageResponse<AdmissionPeriodAdminResponse> getPeriods(BaseFilterRequest filter) {
        Pageable pageable = createPageable(filter);
        Page<AdmissionPeriod> pageData = periodRepository.findAllByDeletedFalse(pageable);

        // Ánh xạ bằng Mapper
        List<AdmissionPeriodAdminResponse> dtoList = pageData.getContent().stream()
                .map(mapper::toPeriodResponse)
                .collect(Collectors.toList());

        return toPageResponse(pageData, dtoList);
    }

    @Override
    public AdmissionPeriodAdminResponse getPeriodById(Integer id) {
        AdmissionPeriod period = periodRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Đợt tuyển sinh với ID: " + id));
        return mapper.toPeriodResponse(period);
    }

    @Override
    @Transactional
    public AdmissionPeriodAdminResponse createPeriod(AdmissionPeriodRequest request) {
        validatePeriodTime(request);

        Map<String, String> errors = admissionPeriodValidator.validate(null, request);

        if (!errors.isEmpty()) {
            throw new InvalidDataException("Dữ liệu không hợp lệ: " + errors.toString());
        }

        // Map từ Request sang Entity
        AdmissionPeriod period = mapper.toPeriodEntity(request);
        return mapper.toPeriodResponse(periodRepository.save(period));
    }

    @Override
    @Transactional
    public AdmissionPeriodAdminResponse updatePeriod(Integer id, AdmissionPeriodRequest request) {
        validatePeriodTime(request);

        AdmissionPeriod period = periodRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Đợt tuyển sinh"));

        Map<String, String> errors = admissionPeriodValidator.validate(id, request);

        if (!errors.isEmpty()) {
            throw new InvalidDataException("Dữ liệu không hợp lệ: " + errors.toString());
        }

        // Dùng Mapper để cập nhật dữ liệu trực tiếp vào Entity hiện tại
        mapper.updatePeriodFromRequest(request, period);

        return mapper.toPeriodResponse(periodRepository.save(period));
    }

    @Override
    @Transactional
    public void deletePeriod(Integer id) {
        AdmissionPeriod period = periodRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Đợt tuyển sinh"));

        boolean hasApplications = applicationRepository.existsByAdmissionPeriodIdAndDeletedFalse(id);
        if (hasApplications) {
            throw new IllegalStateException("Không thể xóa đợt tuyển sinh đã có hồ sơ đăng ký!");
        }

        period.setDeleted(true);
        periodRepository.save(period);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createFullAdmissionCampaign(CreateAdmissionCampaignRequest request) {

        // =================================================================
        // 1. CHẠY VALIDATOR KIỂM TRA ĐỢT TUYỂN SINH
        // =================================================================
        Map<String, String> validationErrors = admissionCampaignValidator.validate(request);
        if (!validationErrors.isEmpty()) {
            // Lấy lỗi đầu tiên trong Map để ném ra cho GlobalExceptionHandler xử lý
            String firstErrorMessage = validationErrors.values().iterator().next();
            throw new InvalidDataException(firstErrorMessage);
        }

        // =================================================================
        // 2. GOM ID VÀ LOAD DỮ LIỆU LÊN RAM (Khử N+1 Query)
        // =================================================================
        if (request.getBenchmarks() == null || request.getBenchmarks().isEmpty()) {
            throw new InvalidDataException("Danh sách cấu hình điểm chuẩn không được để trống!");
        }

        Set<Integer> majorIds = request.getBenchmarks().stream()
                .map(CreateAdmissionCampaignRequest.BenchmarkConfigItem::getMajorId)
                .collect(Collectors.toSet());

        Set<Integer> blockIds = request.getBenchmarks().stream()
                .map(CreateAdmissionCampaignRequest.BenchmarkConfigItem::getBlockId)
                .collect(Collectors.toSet());

        // Lấy toàn bộ Major và Block bằng lệnh IN (...) -> Trả về Map để tra cứu O(1)
        Map<Integer, Major> majorMap = majorRepository.findAllByIdInAndDeletedFalse(majorIds)
                .stream().collect(Collectors.toMap(Major::getId, m -> m));

        Map<Integer, AdmissionBlock> blockMap = blockRepository.findAllByIdInAndDeletedFalse(blockIds)
                .stream().collect(Collectors.toMap(AdmissionBlock::getId, b -> b));

        // =================================================================
        // 3. MAP DỮ LIỆU VÀ LƯU ĐỢT TUYỂN SINH
        // =================================================================
        // Sử dụng Mapper để convert từ DTO sang Entity (chuyển periodName, startTime, endTime)
        AdmissionPeriod newPeriod = mapper.toPeriodEntity(request);

        // Lưu xuống DB để sinh ID
        AdmissionPeriod savedPeriod = periodRepository.save(newPeriod);

        // =================================================================
        // 4. XỬ LÝ DANH SÁCH ĐIỂM CHUẨN (BENCHMARKS)
        // =================================================================
        List<BenchmarkScore> benchmarksToSave = new ArrayList<>();
        Set<String> uniqueChecker = new HashSet<>(); // Chống duplicate tổ hợp Ngành - Khối

        for (CreateAdmissionCampaignRequest.BenchmarkConfigItem item : request.getBenchmarks()) {
            validateScore(item.getScore());

            // Kiểm tra Frontend có gửi trùng dữ liệu không (VD: 2 lần ngành IT khối A00)
            String duplicateKey = item.getMajorId() + "_" + item.getBlockId();
            if (!uniqueChecker.add(duplicateKey)) {
                throw new ExistingResourcesException("Dữ liệu gửi lên bị trùng lặp Ngành và Khối xét tuyển!");
            }

            // Tra cứu từ RAM
            Major major = majorMap.get(item.getMajorId());
            if (major == null) {
                throw new NotFoundResourcesException("Ngành học ID " + item.getMajorId() + " không tồn tại hoặc đã bị xóa.");
            }

            AdmissionBlock block = blockMap.get(item.getBlockId());
            if (block == null) {
                throw new NotFoundResourcesException("Khối xét tuyển ID " + item.getBlockId() + " không tồn tại hoặc đã bị xóa.");
            }

            // Sử dụng Builder để tạo Entity Điểm chuẩn
            BenchmarkScore benchmark = BenchmarkScore.builder()
                    .admissionPeriod(savedPeriod) // Liên kết với đợt vừa tạo ở Bước 3
                    .major(major)
                    .admissionBlock(block)
                    .score(item.getScore())
                    .deleted(false)
                    .build();

            benchmarksToSave.add(benchmark);
        }

        // =================================================================
        // 5. LƯU BATCH TOÀN BỘ ĐIỂM CHUẨN
        // =================================================================
        benchmarkRepository.saveAll(benchmarksToSave);

        log.info("Đã tạo thành công đợt '{}' (ID: {}) và thiết lập {} điểm chuẩn.",
                savedPeriod.getPeriodName(), savedPeriod.getId(), benchmarksToSave.size());
    }

    // =========================================================================
    // 2. QUẢN LÝ KHỐI XÉT TUYỂN
    // =========================================================================

    @Override
    public List<AdmissionBlockResponse> getAllBlocks() {
        List<AdmissionBlock> blocks = blockRepository.findAllByDeletedFalse();
        return mapper.toBlockResponseList(blocks);
    }

    @Override
    @Transactional
    public AdmissionBlockResponse createBlock(BlockRequest request) {
        if (request.getBlockName() == null || request.getBlockName().trim().isEmpty()) {
            throw new InvalidDataException("Tên/Mã khối xét tuyển không được để trống.");
        }
        String blockName = request.getBlockName().trim();
        if (blockRepository.existsByBlockNameAndDeletedFalse(blockName)) {
            throw new ExistingResourcesException("Mã khối xét tuyển '" + request.getBlockName() + "' đã tồn tại!");
        }
        AdmissionBlock block = mapper.toBlockEntity(request);
        block = blockRepository.save(block);

        return mapper.toBlockResponse(block);
    }

    @Override
    @Transactional
    public AdmissionBlockResponse updateBlock(Integer id, BlockRequest request) {
        if (request.getBlockName() == null || request.getBlockName().trim().isEmpty()) {
            throw new InvalidDataException("Tên/Mã khối xét tuyển không được để trống.");
        }
        AdmissionBlock block = blockRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Khối xét tuyển"));
        String newBlockName = request.getBlockName().trim();
        if (!block.getBlockName().equals(newBlockName) &&
                blockRepository.existsByBlockNameAndDeletedFalse(newBlockName)) {
            throw new ExistingResourcesException("Mã khối xét tuyển '" + newBlockName + "' đã tồn tại!");
        }
        mapper.updateBlockFromRequest(request, block);
        block = blockRepository.save(block);
        return mapper.toBlockResponse(block);
    }

    @Override
    @Transactional
    public void deleteBlock(Integer id) {
        AdmissionBlock block = blockRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Khối xét tuyển"));

        // Cần viết thêm hàm existsByAdmissionBlockIdAndDeletedFalse trong benchmarkRepository
        boolean isUsed = benchmarkRepository.existsByAdmissionBlockIdAndDeletedFalse(id);
        if (isUsed) {
            throw new IllegalStateException("Không thể xóa Khối xét tuyển đang được sử dụng trong cấu hình điểm chuẩn!");
        }

        block.setDeleted(true);
        blockRepository.save(block);
    }

    // =========================================================================
    // 3. THIẾT LẬP ĐIỂM CHUẨN
    // =========================================================================

    @Override
    public PageResponse<BenchmarkResponse> getBenchmarks(BenchmarkFilterRequest filter) {
        Pageable pageable = createPageable(filter);
        Page<BenchmarkScore> pageData = benchmarkRepository.findAllByDeletedFalse(pageable);

        List<BenchmarkResponse> dtoList = pageData.getContent().stream()
                .map(mapper::toBenchmarkResponse)
                .collect(Collectors.toList());

        return toPageResponse(pageData, dtoList);
    }

    @Override
    @Transactional
    public void saveBulkBenchmarks(BulkBenchmarkRequest request) {
        if (request.getBenchmarks() == null || request.getBenchmarks().isEmpty()) {
            throw new InvalidDataException("Danh sách điểm chuẩn/cấu hình không được để trống");
        }

        AdmissionPeriod period = periodRepository.findByIdAndDeletedFalse(request.getPeriodId())
                .orElseThrow(() -> new NotFoundResourcesException("Đợt tuyển sinh không tồn tại"));

        // 1. TỐI ƯU HIỆU NĂNG: Gom tất cả ID lại
        Set<Integer> majorIds = request.getBenchmarks().stream()
                .map(BulkBenchmarkRequest.BenchmarkItem::getMajorId).collect(Collectors.toSet());
        Set<Integer> blockIds = request.getBenchmarks().stream()
                .map(BulkBenchmarkRequest.BenchmarkItem::getBlockId).collect(Collectors.toSet());

        // 2. Lấy dữ liệu 1 lần bằng cấu trúc IN (...) và ném lên RAM
        Map<Integer, Major> majorMap = majorRepository.findAllByIdInAndDeletedFalse(majorIds)
                .stream().collect(Collectors.toMap(Major::getId, m -> m));
        Map<Integer, AdmissionBlock> blockMap = blockRepository.findAllByIdInAndDeletedFalse(blockIds)
                .stream().collect(Collectors.toMap(AdmissionBlock::getId, b -> b));

        List<BenchmarkScore> listToSave = new ArrayList<>();

        for (BulkBenchmarkRequest.BenchmarkItem item : request.getBenchmarks()) {
            // 3. Tra cứu trực tiếp từ RAM thay vì chọc xuống DB
            Major major = majorMap.get(item.getMajorId());
            if (major == null) throw new NotFoundResourcesException("Ngành học ID " + item.getMajorId() + " không tồn tại");

            AdmissionBlock block = blockMap.get(item.getBlockId());
            if (block == null) throw new NotFoundResourcesException("Khối xét tuyển ID " + item.getBlockId() + " không tồn tại");

            validateScore(item.getScore());

            // Lệnh findBy này bắt buộc phải query vì nó check ghép 3 khóa.
            // (Nếu muốn tối ưu mức độ cao nhất thì cũng có thể query IN 3 khóa, nhưng với quy mô dự án hiện tại, tối ưu Major và Block như trên là đã cải thiện 66% tốc độ rồi).
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
            listToSave.add(benchmark);
        }

        benchmarkRepository.saveAll(listToSave);
    }

    @Override
    @Transactional
    public BenchmarkResponse updateBenchmark(Integer id, BenchmarkRequest request) {
        validateScore(request.getScore());
        BenchmarkScore benchmark = benchmarkRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Điểm chuẩn không tồn tại"));
        benchmark.setScore(request.getScore());

        return mapper.toBenchmarkResponse(benchmarkRepository.save(benchmark));
    }

    @Override
    @Transactional
    public void deleteBenchmark(Integer id) {
        BenchmarkScore benchmark = benchmarkRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Điểm chuẩn không tồn tại"));
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
        List<Map<String, Object>> periods = periodRepository.findAllByDeletedFalse().stream()
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



    // Ham ho tro
    private void validateScore(Float score) {
        if (score == null || score < 0 || score > 30) {
            throw new InvalidDataException("Điểm chuẩn phải nằm trong khoảng từ 0 đến 30");
        }
    }

    private void validatePeriodTime(AdmissionPeriodRequest request) {
        if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new InvalidDataException("Thời gian bắt đầu và kết thúc không được để trống");
        }
        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new InvalidDataException("Thời gian bắt đầu không được lớn hơn thời gian kết thúc");
        }
    }

    private Pageable createPageable(BaseFilterRequest filter) {
        // 1. Xử lý Direction an toàn
        Sort.Direction direction = Sort.Direction.DESC; // Default
        if (filter.getSortDirection() != null && filter.getSortDirection().equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }

        // 2. Xử lý Sort By an toàn (Tránh null)
        String sortBy = (filter.getSortBy() != null && !filter.getSortBy().trim().isEmpty())
                ? filter.getSortBy() : "id";

        Sort sort = Sort.by(direction, sortBy);

        // 3. Xử lý Index Trang (JPA index từ 0)
        int pageNo = filter.getPage() > 0 ? filter.getPage() - 1 : 0;

        // 4. Giới hạn size tránh quá tải DB (VD: max 100)
        int pageSize = filter.getSize() > 0 ? Math.min(filter.getSize(), 100) : 10;

        return PageRequest.of(pageNo, pageSize, sort);
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