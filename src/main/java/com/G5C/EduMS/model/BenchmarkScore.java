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

    @Column(name = "admission_year")
    private Integer admissionYear;

    @Column(name = "score")
    private Float score;
}

