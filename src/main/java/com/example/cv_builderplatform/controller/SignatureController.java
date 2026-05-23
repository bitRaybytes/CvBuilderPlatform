package com.example.cv_builderplatform.controller;

import com.example.cv_builderplatform.dto.cv.SignatureDTO;
import com.example.cv_builderplatform.services.SignatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cv/signature")
public class SignatureController {

    private final SignatureService service;

    // @PostMapping
    // public ResponseEntity<SignatureDTO> saveSignature(
    //         @AuthenticationPrincipal UserDetails userDetails,
    //         @RequestBody SignatureDTO dto){
    //     SignatureDTO saved = service.addSignature(userDetails.getUsername(),dto);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    // }

    @PostMapping
    public ResponseEntity<SignatureDTO> saveSignature(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody SignatureDTO dto){
        SignatureDTO saved = service.addSignature(userDetails.getUsername(),dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/upload-signature")
    public ResponseEntity<SignatureDTO> uploadSignatureFile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("file") MultipartFile file) throws FileNotFoundException {
        SignatureDTO saved = service.uploadSignature(userDetails.getUsername(), file);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SignatureDTO> updateSignature(
            @PathVariable UUID id,
            @RequestBody SignatureDTO dto){
        SignatureDTO updated = service.updateSignature(id,dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSignature(@PathVariable UUID id){
        service.deleteSignature(id);
        return ResponseEntity.noContent().build();
    }

}
