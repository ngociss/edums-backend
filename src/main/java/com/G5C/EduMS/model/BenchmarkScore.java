package com.G5C.EduMS.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "benchmark_scores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BenchmarkScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id")
    private AdmissionBlock admissionBlock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_id", nullable = false)
    private AdmissionPeriod admissionPeriod;

    @Column(name = "score")
    private Float score;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;
}

