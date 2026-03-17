package com.example.cv_builderplatform.controller;

import com.example.cv_builderplatform.dto.cv.EducationDTO;
import com.example.cv_builderplatform.services.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cv/educations")
public class EducationController {

    private final EducationService service;

    @PostMapping
    public ResponseEntity<EducationDTO> saveEducation(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody EducationDTO dto){
        EducationDTO saved = service.addEducation(userDetails.getUsername(),dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EducationDTO> updateEducation(
            @PathVariable UUID id,
            @RequestBody EducationDTO dto){
        EducationDTO updated = service.updateEducation(id,dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducation(
            @PathVariable UUID id){
        service.deleteEducation(id);
        return ResponseEntity.noContent().build();
    }

}
