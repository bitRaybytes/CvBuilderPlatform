package com.example.cv_builderplatform.dto.cv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CertificateDTO {
    private UUID id;
    private String title;
    private String issuer;
    private LocalDate dateIssued;
}
