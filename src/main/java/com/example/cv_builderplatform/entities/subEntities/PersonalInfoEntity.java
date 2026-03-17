package com.example.cv_builderplatform.entities.subEntities;

import com.example.cv_builderplatform.entities.CvEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "personals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "cv_id")
    private CvEntity cv;
    private String firstname;
    private String lastname;
    private String street;
    private String city;

    @Column(name = "zipCode")
    private String zip;
    private String country;
    private String phone;

    @Column(name = "cvEmail")
    private String email;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;
    private String birthplace;
    private String photoPath;
    private String summary;     // Kurzprofil / Über mich

    private Integer sortOrder;

}
