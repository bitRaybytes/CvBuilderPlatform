package com.example.cv_builderplatform.controller;

import com.example.cv_builderplatform.dto.cv.CertificateDTO;
import com.example.cv_builderplatform.services.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cv/certificates")
public class CertificateController {

    private final CertificateService service;

    @PostMapping
    public ResponseEntity<CertificateDTO> saveCertificate(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CertificateDTO dto){
        CertificateDTO saved = service.addCertificate(userDetails.getUsername(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificateDTO> updateCertificate(
            @PathVariable UUID id,
            @RequestBody CertificateDTO dto){
        CertificateDTO updated = service.updatedCertificate(id,dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(
            @PathVariable UUID id){
        service.deleteCertificate(id);
        return ResponseEntity.noContent().build();
    }
}
