package com.example.cv_builderplatform.entities.subEntities;


import com.example.cv_builderplatform.entities.CvEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "internships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternshipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    private CvEntity cv;

    private String company;
    private String role;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String description;
    private Integer sortOrder;
}
