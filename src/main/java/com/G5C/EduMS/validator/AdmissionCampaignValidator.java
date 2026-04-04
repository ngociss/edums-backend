package com.G5C.EduMS.validator;

import com.G5C.EduMS.dto.request.CreateAdmissionCampaignRequest;
import com.G5C.EduMS.repository.AdmissionBlockRepository;
import com.G5C.EduMS.repository.AdmissionPeriodRepository;
import com.G5C.EduMS.repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdmissionCampaignValidator {

    private final AdmissionPeriodRepository periodRepository;
    private final MajorRepository majorRepository;
    private final AdmissionBlockRepository blockRepository;

    /**
     * Hàm kiểm tra toàn diện DTO CreateAdmissionCampaignRequest.
     * Trả về Map chứa danh sách lỗi. Nếu Map rỗng -> Dữ liệu hợp lệ 100%.
     */
    public Map<String, String> validate(CreateAdmissionCampaignRequest request) {
        Map<String, String> errors = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();

        // =========================================================
        // 1. KIỂM TRA THÔNG TIN ĐỢT TUYỂN SINH (PERIOD)
        // =========================================================

        // 1.1 Tên đợt tuyển sinh
        if (request.getPeriodName() == null || request.getPeriodName().trim().isEmpty()) {
            errors.put("periodName", "Tên đợt tuyển sinh không được để trống.");
        } else if (request.getPeriodName().length() > 255) {
            errors.put("periodName", "Tên đợt tuyển sinh không được vượt quá 255 ký tự.");
        } else if (periodRepository.existsByPeriodNameAndDeletedFalse(request.getPeriodName().trim())) {
            errors.put("periodName", "Tên đợt tuyển sinh này đã tồn tại trong hệ thống.");
        }

        // 1.2 Thời gian bắt đầu & kết thúc
        boolean isTimeValid = true;

        if (request.getStartTime() == null) {
            errors.put("startTime", "Thời gian bắt đầu không được để trống.");
            isTimeValid = false;
        } else if (request.getStartTime().isBefore(now)) {
            errors.put("startTime", "Thời gian bắt đầu không được nằm trong quá khứ.");
            isTimeValid = false;
        }

        if (request.getEndTime() == null) {
            errors.put("endTime", "Thời gian kết thúc không được để trống.");
            isTimeValid = false;
        }

        // 1.3 Logic thời gian (Kết thúc > Bắt đầu) và Trùng lịch (Overlap)
        if (isTimeValid) {
            if (!request.getEndTime().isAfter(request.getStartTime())) {
                errors.put("endTime", "Thời gian kết thúc bắt buộc phải nằm sau thời gian bắt đầu.");
            } else {
                // Gọi DB kiểm tra xem có cắt ngang đợt nào đang có không
                boolean isOverlapping = periodRepository.existsOverlappingPeriod(
                        request.getStartTime(),
                        request.getEndTime()
                );
                if (isOverlapping) {
                    errors.put("timeRange", "Thời gian này đang bị chồng chéo với một đợt tuyển sinh khác trong hệ thống.");
                }
            }
        }

        // =========================================================
        // 2. KIỂM TRA DANH SÁCH ĐIỂM CHUẨN (BENCHMARKS)
        // =========================================================

        if (request.getBenchmarks() == null || request.getBenchmarks().isEmpty()) {
            errors.put("benchmarks", "Phải cấu hình ít nhất 1 điểm chuẩn cho đợt tuyển sinh.");
            return errors; // Dừng sớm nếu không có mảng này
        }

        Set<String> uniqueCombinations = new HashSet<>();
        Set<Integer> majorIds = new HashSet<>();
        Set<Integer> blockIds = new HashSet<>();

        // Duyệt qua từng item để kiểm tra Null, Range và Duplicate
        for (int i = 0; i < request.getBenchmarks().size(); i++) {
            CreateAdmissionCampaignRequest.BenchmarkConfigItem item = request.getBenchmarks().get(i);
            String rowPrefix = "benchmarks[" + i + "].";

            // Null checks
            if (item.getMajorId() == null) {
                errors.put(rowPrefix + "majorId", "ID Ngành học không được để trống.");
            } else {
                majorIds.add(item.getMajorId());
            }

            if (item.getBlockId() == null) {
                errors.put(rowPrefix + "blockId", "ID Khối xét tuyển không được để trống.");
            } else {
                blockIds.add(item.getBlockId());
            }

            // Điểm số checks (0 - 40)
            if (item.getScore() == null) {
                errors.put(rowPrefix + "score", "Điểm chuẩn không được để trống.");
            } else if (item.getScore() < 0.0f || item.getScore() > 40.0f) {
                errors.put(rowPrefix + "score", "Điểm chuẩn phải nằm trong khoảng từ 0.0 đến 40.0.");
            }

            // Kiểm tra trùng lặp tổ hợp Ngành + Khối trong CHÍNH REQUEST này
            if (item.getMajorId() != null && item.getBlockId() != null) {
                String comboKey = item.getMajorId() + "_" + item.getBlockId();
                if (!uniqueCombinations.add(comboKey)) {
                    errors.put(rowPrefix + "duplicate", "Tổ hợp Ngành học và Khối này bị khai báo trùng lặp nhiều lần.");
                }
            }
        }

        // =========================================================
        // 3. KIỂM TRA SỰ TỒN TẠI DƯỚI DATABASE (Tối ưu N+1)
        // =========================================================
        // Chỉ gọi DB kiểm tra nếu list không có lỗi rỗng null ở trên
        if (!majorIds.isEmpty()) {
            long existingMajorsCount = majorRepository.countByIdInAndDeletedFalse(majorIds);
            if (existingMajorsCount != majorIds.size()) {
                errors.put("majorIds", "Một hoặc nhiều Ngành học không tồn tại trong hệ thống.");
            }
        }

        if (!blockIds.isEmpty()) {
            long existingBlocksCount = blockRepository.countByIdInAndDeletedFalse(blockIds);
            if (existingBlocksCount != blockIds.size()) {
                errors.put("blockIds", "Một hoặc nhiều Khối xét tuyển không tồn tại trong hệ thống.");
            }
        }

        return errors;
    }
}