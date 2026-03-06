package com.G5C.EduMS.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "classrooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "room_name", length = 255)
    private String roomName;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "room_type", length = 255)
    private String roomType; // e.g., Theory, Lab

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<RecurringSchedule> recurringSchedules;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<ClassSession> classSessions;
}
