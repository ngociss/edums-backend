package com.G5C.EduMS.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "lecturers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "full_name", length = 50)
    private String fullName;

    @Column(name = "academic_degree", length = 50)
    private String academicDegree;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "headLecturer", fetch = FetchType.LAZY)
    private List<AdministrativeClass> managedClasses;

    @OneToMany(mappedBy = "lecturer", fetch = FetchType.LAZY)
    private List<CourseSection> courseSections;
}
