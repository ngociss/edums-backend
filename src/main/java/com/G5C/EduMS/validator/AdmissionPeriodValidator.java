package com.G5C.EduMS.validator;

import com.G5C.EduMS.dto.request.AdmissionPeriodRequest;
import com.G5C.EduMS.repository.AdmissionPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AdmissionPeriodValidator {

    private final AdmissionPeriodRepository periodRepository;

    /**
     * Hàm kiểm tra toàn diện. Trả về một Map chứa danh sách các lỗi.
     * Nếu Map rỗng (isEmpty) nghĩa là dữ liệu hợp lệ 100%.
     */
    public Map<String, String> validate(Integer id, AdmissionPeriodRequest request) {
        Map<String, String> errors = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();

        // ==========================================
        // 1. KIỂM TRA TÊN ĐỢT TUYỂN SINH
        // ==========================================
        if (request.getPeriodName() == null || request.getPeriodName().trim().isEmpty()) {
            errors.put("periodName", "Tên đợt tuyển sinh không được để trống.");
        } else if (request.getPeriodName().length() > 255) {
            errors.put("periodName", "Tên đợt tuyển sinh không được vượt quá 255 ký tự.");
        } else if (periodRepository.existsByNameAndNotId(request.getPeriodName(), id)) {
            errors.put("periodName", "Tên đợt tuyển sinh này đã tồn tại trong hệ thống.");
        }

        // ==========================================
        // 2. KIỂM TRA THỜI GIAN
        // ==========================================
        if (request.getStartTime() == null) {
            errors.put("startTime", "Thời gian bắt đầu không được để trống.");
        } else if (id == null && request.getStartTime().isBefore(now)) {
            // Chỉ bắt lỗi quá khứ khi TẠO MỚI (id == null)
            errors.put("startTime", "Thời gian bắt đầu không được nằm trong quá khứ.");
        }
        if (request.getEndTime() == null) {
            errors.put("endTime", "Thời gian kết thúc không được để trống.");
        }

        // ==========================================
        // 3. KIỂM TRA LOGIC THỜI GIAN & TRÙNG LỊCH (OVERLAP)
        // ==========================================
        if (request.getStartTime() != null && request.getEndTime() != null) {

            // A. Kiểm tra kết thúc phải sau bắt đầu
            if (!request.getEndTime().isAfter(request.getStartTime())) {
                errors.put("endTime", "Thời gian kết thúc bắt buộc phải sau thời gian bắt đầu.");
            }
            // B. Nếu 2 ngày đã hợp lý -> Mới gọi DB để kiểm tra đụng lịch đợt khác
            else {
                boolean isOverlapping = periodRepository.existsOverlappingPeriod(
                        request.getStartTime(),
                        request.getEndTime(),
                        id
                );

                if (isOverlapping) {
                    errors.put("timeRange", "Lịch này đang bị trùng/chồng chéo với một đợt tuyển sinh khác.");
                }
            }
        }

        return errors; // Trả về danh sách lỗi
    }
}