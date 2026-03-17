package com.example.cv_builderplatform.dto.cv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExperienceDTO {
    private UUID id;
    private String company;
    private String role;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String description;
}
