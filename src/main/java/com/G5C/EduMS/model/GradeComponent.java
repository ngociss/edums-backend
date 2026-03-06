package com.G5C.EduMS.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "grade_components",
        indexes = @Index(name = "idx_grade_components_course_name",
                columnList = "course_id, component_name"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "component_name", length = 50)
    private String componentName; // e.g., Attendance, Midterm, Final

    @Column(name = "weight_percentage")
    private Float weightPercentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "component", fetch = FetchType.LAZY)
    private List<GradeDetail> gradeDetails;
}
