package com.G5C.EduMS.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "majors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @Column(name = "major_name", length = 255)
    private String majorName;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "major", fetch = FetchType.LAZY)
    private List<Specialization> specializations;

    @OneToMany(mappedBy = "major", fetch = FetchType.LAZY)
    private List<AdministrativeClass> administrativeClasses;

    @OneToMany(mappedBy = "major", fetch = FetchType.LAZY)
    private List<AdmissionApplication> admissionApplications;

    @OneToMany(mappedBy = "major", fetch = FetchType.LAZY)
    private List<BenchmarkScore> benchmarkScores;
}
