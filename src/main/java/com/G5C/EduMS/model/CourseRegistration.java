package com.G5C.EduMS.model;

import com.G5C.EduMS.common.enums.RegistrationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "course_registrations",
        uniqueConstraints = @UniqueConstraint(name = "uk_student_section",
                columnNames = {"student_id", "section_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private CourseSection section;

    @Column(name = "registration_time")
    private LocalDateTime registrationTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    @Builder.Default
    private RegistrationStatus status = RegistrationStatus.PENDING;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "registration", fetch = FetchType.LAZY)
    private List<Attendance> attendances;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "registration_period_id", nullable = false)
    private RegistrationPeriod registrationPeriod;

    @OneToOne(mappedBy = "registration", fetch = FetchType.LAZY)
    private GradeReport gradeReport;
}
