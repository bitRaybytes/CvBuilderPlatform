package com.example.cv_builderplatform.controller;

import com.example.cv_builderplatform.dto.cv.SkillDTO;
import com.example.cv_builderplatform.services.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cv/skills")
public class SkillController {
    private final SkillService service;

    @PostMapping
    public ResponseEntity<SkillDTO> saveSkill(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody SkillDTO dto){
        SkillDTO saved = service.addSkill(userDetails.getUsername(),dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillDTO> updateSkill(
            @PathVariable UUID id,
            @RequestBody SkillDTO dto){
        SkillDTO updated = service.updateSkill(id,dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(
            @PathVariable UUID id){
        service.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
}
