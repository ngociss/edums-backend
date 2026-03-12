package com.G5C.EduMS.model;

import com.G5C.EduMS.common.enums.CourseSectionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "course_sections",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_section_code_semester",
                columnNames = {"section_code", "semester_id"}
        ))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "section_code", length = 50, nullable = false)
    private String sectionCode;

    @Column(name = "display_name", length = 255)
    private String displayName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;


    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    @Builder.Default
    private CourseSectionStatus status = CourseSectionStatus.DRAFT;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
    private List<RecurringSchedule> recurringSchedules;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
    private List<ClassSession> classSessions;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
    private List<CourseRegistration> courseRegistrations;
}
