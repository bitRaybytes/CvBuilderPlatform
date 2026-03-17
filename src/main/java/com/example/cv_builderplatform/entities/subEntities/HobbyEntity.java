package com.example.cv_builderplatform.entities.subEntities;

import com.example.cv_builderplatform.entities.CvEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "hobbies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HobbyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    private CvEntity cv;

    private String name;
    private Integer sortOrder;
}
