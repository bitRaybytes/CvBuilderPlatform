package com.example.cv_builderplatform.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cv")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CvEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    private UUID id;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private UserEntity user;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
