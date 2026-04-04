package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.AdmissionPeriodStatus;
import com.G5C.EduMS.dto.request.ApplicationSubmitRequest;
import com.G5C.EduMS.dto.response.PublicLookupResponse;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.PublicAdmissionMapper;
import com.G5C.EduMS.model.*;
import com.G5C.EduMS.repository.*;
import com.G5C.EduMS.service.PublicAdmissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicAdmissionServiceImpl implements PublicAdmissionService {

    private final AdmissionApplicationRepository applicationRepository;
    private final AdmissionPeriodRepository periodRepository;
    private final MajorRepository majorRepository;
    private final AdmissionBlockRepository blockRepository;
    private final BenchmarkScoreRepository benchmarkRepository; // Thêm Repo này
    private final PublicAdmissionMapper mapper;

    @Override
    public List<Map<String, Object>> getActivePeriods() {
        return periodRepository.findActivePeriods(AdmissionPeriodStatus.OPEN)
                .stream().map(mapper::toPeriodMap).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getAvailableMajors(Integer periodId) {
        if (periodId == null) {
            throw new InvalidDataException("ID Đợt tuyển sinh không được để trống.");
        }
        return benchmarkRepository.findDistinctMajorsByPeriodId(periodId).stream()
                .map(mapper::toMajorMap)
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getAvailableBlocks(Integer periodId, Integer majorId) {
        if (periodId == null || majorId == null) {
            throw new InvalidDataException("ID Đợt tuyển sinh và ID Ngành học không được để trống.");
        }
        return benchmarkRepository.findDistinctBlocksByPeriodIdAndMajorId(periodId, majorId).stream()
                .map(mapper::toBlockMap)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void submitApplication(ApplicationSubmitRequest request) {
        AdmissionPeriod period = periodRepository.findByIdAndDeletedFalse(request.getPeriodId())
                .orElseThrow(() -> new IllegalArgumentException("Đợt tuyển sinh không tồn tại."));

        boolean isExpired = period.getEndTime() != null && LocalDateTime.now().isAfter(period.getEndTime());
        if (period.getStatus() != AdmissionPeriodStatus.OPEN || isExpired) {
            throw new ExistingResourcesException("Đợt tuyển sinh này đã đóng hoặc hết hạn.");
        }

        if (applicationRepository.existsByNationalIdAndAdmissionPeriodIdAndDeletedFalse(request.getNationalId(), period.getId())) {
            throw new ExistingResourcesException("CCCD này đã được sử dụng để nộp hồ sơ trong đợt hiện tại.");
        }

        Major major = majorRepository.findById(request.getMajorId())
                .orElseThrow(() -> new NotFoundResourcesException("Ngành học không tồn tại."));
        AdmissionBlock block = blockRepository.findById(request.getBlockId())
                .orElseThrow(() -> new NotFoundResourcesException("Khối xét tuyển không tồn tại."));

        // Validate thêm: Ngành và Khối này có thực sự được cấu hình cho đợt này không?
        boolean isValidConfig = benchmarkRepository.findByMajorIdAndAdmissionBlockIdAndAdmissionPeriodIdAndDeletedFalse(
                major.getId(), block.getId(), period.getId()).isPresent();

        if (!isValidConfig) {
            throw new IllegalArgumentException("Tổ hợp Ngành và Khối xét tuyển này không mở trong đợt hiện tại.");
        }

        AdmissionApplication application = mapper.toEntity(request, period, major, block);
        applicationRepository.save(application);
        log.info("Thành công: Thí sinh {} đã nộp hồ sơ.", request.getFullName());
    }

    // Trả về List để xử lý trường hợp nộp nhiều đợt
    @Override
    public List<PublicLookupResponse> lookupApplication(String nationalId, String phone) {
        if (nationalId == null || nationalId.trim().isEmpty() || phone == null || phone.trim().isEmpty()) {
            throw new InvalidDataException("Vui lòng nhập đầy đủ CCCD và Số điện thoại.");
        }
        List<AdmissionApplication> applications = applicationRepository
                .findAllByNationalIdAndPhoneAndDeletedFalse(nationalId.trim(), phone.trim());

        if (applications.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy hồ sơ. Vui lòng kiểm tra lại CCCD và Số điện thoại.");
        }

        return applications.stream()
                .map(mapper::toLookupResponse)
                .collect(Collectors.toList());
    }
}