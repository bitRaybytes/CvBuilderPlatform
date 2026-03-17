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
public class EducationDTO {
    private UUID id;
    private String institution;
    private String degree;
    private String fieldOfStudy;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
