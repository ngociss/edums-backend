package com.G5C.EduMS.model;

import com.G5C.EduMS.common.enums.StudentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", unique = true)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private AdministrativeClass administrativeClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guardian_id")
    private Guardian guardian;

    @Column(name = "student_code", length = 10, unique = true)
    private String studentCode;

    @Column(name = "full_name", length = 50)
    private String fullName;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private Boolean gender; // true = Male, false = Female

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "national_id", length = 12, unique = true)
    private String nationalId;

    @Column(name = "ethnicity", length = 20)
    private String ethnicity;

    @Column(name = "religion", length = 50)
    private String religion;

    @Column(name = "place_of_birth", length = 50)
    private String placeOfBirth;

    @Column(name = "nationality", length = 50)
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    @Builder.Default
    private StudentStatus status = StudentStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<CourseRegistration> courseRegistrations;
}
