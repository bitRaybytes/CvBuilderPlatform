package com.example.cv_builderplatform.controller;

import com.example.cv_builderplatform.dto.cv.CvResponseDTO;
import com.example.cv_builderplatform.services.CvService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cv")
public class CvController {

    private final CvService cvService;

    @GetMapping
    public ResponseEntity<CvResponseDTO> loadCv(
            @AuthenticationPrincipal UserDetails userDetails) {
        CvResponseDTO dto = cvService.loadFullCvByUsername(userDetails.getUsername());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCvEntity(@PathVariable UUID id){
        cvService.deleteCv(id);
        return ResponseEntity.noContent().build();
    }


}
