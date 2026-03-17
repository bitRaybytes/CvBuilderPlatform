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
@Table(name = "signatures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignatureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "cv_id")
    private CvEntity cv;

    private String city;
    private LocalDate signatureDate;
    private String signaturePath;

}
