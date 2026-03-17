package com.G5C.EduMS.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "semesters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "semester_number")
    private Integer semesterNumber;

    @Column(name = "academic_year", length = 50)
    private String academicYear;

    /** Ngày bắt đầu học kỳ — dùng để sinh ClassSession tự động */
    @Column(name = "start_date")
    private LocalDate startDate;

    /** Ngày kết thúc học kỳ — dùng để sinh ClassSession tự động */
    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY)
    private List<CourseSection> courseSections;
}
