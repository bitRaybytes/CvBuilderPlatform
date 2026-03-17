package com.example.cv_builderplatform.controller;

import com.example.cv_builderplatform.dto.cv.VolunteerDTO;
import com.example.cv_builderplatform.services.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cv/volunteers")
public class VolunteerController {

    private final VolunteerService service;

    @PostMapping
    public ResponseEntity<VolunteerDTO> saveVolunteer(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody VolunteerDTO dto){
        VolunteerDTO saved = service.addVolunteer(userDetails.getUsername(),dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VolunteerDTO> updatedVolunteer(
            @PathVariable UUID id,
            @RequestBody VolunteerDTO dto){
        VolunteerDTO updated = service.updateVolunteer(id,dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(
            @PathVariable UUID id){
        service.deleteVolunteer(id);
        return ResponseEntity.noContent().build();
    }

}
