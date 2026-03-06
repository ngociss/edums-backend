package com.G5C.EduMS.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "grade_details",
        uniqueConstraints = @UniqueConstraint(name = "uk_report_component",
                columnNames = {"report_id", "component_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private GradeReport report;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id")
    private GradeComponent component;

    @Column(name = "score")
    private Float score;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;
}
