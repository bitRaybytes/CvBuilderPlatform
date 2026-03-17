package com.example.cv_builderplatform.controller;

import com.example.cv_builderplatform.dto.cv.InternshipDTO;
import com.example.cv_builderplatform.services.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cv/internships")
public class InternshipController {

    private final InternshipService service;

    @PostMapping
    public ResponseEntity<InternshipDTO> saveInternship(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody InternshipDTO dto){
        InternshipDTO saved = service.addInternship(userDetails.getUsername(),dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InternshipDTO> updateInternship(
            @PathVariable UUID id,
            @RequestBody InternshipDTO dto){
        InternshipDTO updated = service.updateInternship(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHobby( @PathVariable UUID id){
        service.deleteInternship(id);
        return ResponseEntity.noContent().build();
    }

}