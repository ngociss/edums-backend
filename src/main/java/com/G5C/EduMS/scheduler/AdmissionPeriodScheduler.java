package com.G5C.EduMS.scheduler;

import com.G5C.EduMS.common.enums.AdmissionPeriodStatus;
import com.G5C.EduMS.model.AdmissionPeriod;
import com.G5C.EduMS.repository.AdmissionPeriodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdmissionPeriodScheduler {

    private final AdmissionPeriodRepository periodRepository;

    /**
     * Hàm này sẽ tự động chạy mỗi phút 1 lần (cron = "0 * * * * *")
     * Bạn có thể đổi thành chạy mỗi giờ 1 lần: cron = "0 0 * * * *"
     */
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void autoUpdatePeriodStatus() {
        LocalDateTime now = LocalDateTime.now();
        int updatedCount = 0;

        // 1. Tự động MỞ (UPCOMING -> OPEN)
        List<AdmissionPeriod> upcomingPeriods = periodRepository.findByStatusAndDeletedFalse(AdmissionPeriodStatus.UPCOMING);
        for (AdmissionPeriod period : upcomingPeriods) {
            if (now.isAfter(period.getStartTime()) || now.isEqual(period.getStartTime())) {
                period.setStatus(AdmissionPeriodStatus.OPEN);
                updatedCount++;
                log.info("Đã TỰ ĐỘNG MỞ đợt tuyển sinh ID: {}", period.getId());
            }
        }

        // 2. Tự động ĐÓNG (OPEN -> CLOSED)
        List<AdmissionPeriod> openPeriods = periodRepository.findByStatusAndDeletedFalse(AdmissionPeriodStatus.OPEN);
        for (AdmissionPeriod period : openPeriods) {
            if (now.isAfter(period.getEndTime()) || now.isEqual(period.getEndTime())) {
                period.setStatus(AdmissionPeriodStatus.CLOSED);
                updatedCount++;
                log.info("Đã TỰ ĐỘNG ĐÓNG đợt tuyển sinh ID: {}", period.getId());
            }
        }

        // Lưu toàn bộ thay đổi (nếu có)
        if (updatedCount > 0) {
            periodRepository.saveAll(upcomingPeriods);
            periodRepository.saveAll(openPeriods);
        }
    }
}