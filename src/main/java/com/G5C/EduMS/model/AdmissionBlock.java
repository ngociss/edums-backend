package com.G5C.EduMS.model;

import com.G5C.EduMS.common.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "admission_blocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "block_name", length = 5, nullable = false)
    private String blockName;

    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;
}