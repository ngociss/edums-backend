package com.G5C.EduMS.model;

import com.G5C.EduMS.common.enums.AdmissionPeriodStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "admission_periods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "period_name", length = 255, nullable = false)
    private String periodName;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    // Sử dụng EnumType.ORDINAL để lưu xuống DB dưới dạng số nguyên (0, 1, 2)
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private AdmissionPeriodStatus status = AdmissionPeriodStatus.UPCOMING;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    public void updateStatusBasedOnTime() {
        if (this.startTime == null || this.endTime == null) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(this.startTime)) {
            this.status = AdmissionPeriodStatus.UPCOMING;
        } else if (now.isAfter(this.endTime)) {
            this.status = AdmissionPeriodStatus.CLOSED;
        } else {
            this.status = AdmissionPeriodStatus.OPEN;
        }
    }

    @PrePersist
    @PreUpdate
    public void onPrePersistOrUpdate() {
        updateStatusBasedOnTime();
    }

    @PostLoad
    public void onPostLoad() {
        updateStatusBasedOnTime();
    }
}
