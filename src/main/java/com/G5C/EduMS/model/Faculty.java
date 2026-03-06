package com.G5C.EduMS.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "faculties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "faculty_name", length = 255)
    private String facultyName;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY)
    private List<Major> majors;

    @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY)
    private List<Course> courses;
}
