package com.G5C.EduMS.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "administrative_classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdministrativeClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "class_name", length = 255)
    private String className;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_lecturer_id")
    private Lecturer headLecturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cohort_id")
    private Cohort cohort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "administrativeClass", fetch = FetchType.LAZY)
    private List<Student> students;
}
