package com.G5C.EduMS.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recurring_schedules",
        indexes = @Index(name = "idx_recurring_room_day_period",
                columnList = "room_id, day_of_week, start_period"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecurringSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private CourseSection section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Classroom room;

    @Column(name = "day_of_week")
    private Integer dayOfWeek; // 2 (Mon) to 8 (Sun)

    @Column(name = "start_period")
    private Integer startPeriod;

    @Column(name = "end_period")
    private Integer endPeriod;
}

