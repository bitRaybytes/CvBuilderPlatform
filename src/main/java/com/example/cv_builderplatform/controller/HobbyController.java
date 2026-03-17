package com.example.cv_builderplatform.controller;


import com.example.cv_builderplatform.dto.cv.HobbyDTO;
import com.example.cv_builderplatform.services.HobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cv/hobbies")
public class HobbyController {

    private final HobbyService service;

    @PostMapping
    public ResponseEntity<HobbyDTO> addHobby(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody HobbyDTO dto) {
        HobbyDTO saved = service.addHobby(userDetails.getUsername(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HobbyDTO> updateHobby(
            @PathVariable UUID id,
            @RequestBody HobbyDTO dto) {
        HobbyDTO updated = service.updateHobby(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHobby(@PathVariable UUID id) {
        service.deleteHobby(id);
        return ResponseEntity.noContent().build();
    }
}
