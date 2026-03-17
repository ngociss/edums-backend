package com.G5C.EduMS.model;

import com.G5C.EduMS.common.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "class_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private CourseSection section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Classroom room;

    /** Liên kết về RecurringSchedule đã sinh ra buổi học này */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurring_schedule_id")
    private RecurringSchedule recurringSchedule;

    @Column(name = "session_date")
    private LocalDate sessionDate;

    @Column(name = "start_period")
    private Integer startPeriod;

    @Column(name = "end_period")
    private Integer endPeriod;

    @Column(name = "lesson_content", columnDefinition = "TEXT")
    private String lessonContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    @Builder.Default
    private SessionStatus status = SessionStatus.NORMAL;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
    private List<Attendance> attendances;
}
