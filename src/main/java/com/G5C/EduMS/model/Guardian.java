package com.G5C.EduMS.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "guardians")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Guardian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", unique = true)
    private Account account;

    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;

    @Column(name = "phone", length = 10, nullable = false)
    private String phone;

    @Column(name = "relationship", length = 50)
    private String relationship; // e.g., Father, Mother, Guardian

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "guardian", fetch = FetchType.LAZY)
    private List<Student> students;
}
