package com.example.cv_builderplatform.controller;

import com.example.cv_builderplatform.dto.cv.ExperienceDTO;
import com.example.cv_builderplatform.services.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cv/experiences")
public class ExperienceController {

    private final ExperienceService service;

    @PostMapping
    public ResponseEntity<ExperienceDTO> saveExperience(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ExperienceDTO dto){
        ExperienceDTO saved = service.addExperience(userDetails.getUsername(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExperienceDTO> updateExperience(
            @PathVariable UUID id,
            @RequestBody ExperienceDTO dto){
        ExperienceDTO updated = service.updateExperience(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(
            @PathVariable UUID id){
        service.deleteExperience(id);
        return ResponseEntity.noContent().build();
    }

}
