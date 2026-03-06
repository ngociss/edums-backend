package com.G5C.EduMS.model;

import com.G5C.EduMS.common.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attendance",
        uniqueConstraints = @UniqueConstraint(name = "uk_session_registration",
                columnNames = {"session_id", "registration_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private ClassSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id")
    private CourseRegistration registration;

    @Enumerated(EnumType.STRING)
    @Column(name = "attendance_status", length = 20)
    private AttendanceStatus attendanceStatus;

    @Column(name = "note", length = 255)
    private String note;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;
}
