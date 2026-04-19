package com.G5C.EduMS.scheduler;

import com.G5C.EduMS.service.AcademicStatusSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AcademicStatusScheduler {

    private final AcademicStatusSyncService academicStatusSyncService;

    @Scheduled(cron = "0 * * * * *")
    public void autoUpdateAcademicStatuses() {
        academicStatusSyncService.syncAll();
    }
}
