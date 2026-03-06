package com.G5C.EduMS.model;

import com.G5C.EduMS.common.enums.GradeStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "grade_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id", unique = true)
    private CourseRegistration registration;

    @Column(name = "final_score")
    private Float finalScore;

    @Column(name = "letter_grade", length = 5)
    private String letterGrade;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    @Builder.Default
    private GradeStatus status = GradeStatus.DRAFT;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY)
    private List<GradeDetail> gradeDetails;
}
