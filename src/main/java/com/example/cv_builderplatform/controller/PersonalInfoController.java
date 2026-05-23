package com.example.cv_builderplatform.controller;

import com.example.cv_builderplatform.dto.cv.PersonalInfoDTO;
import com.example.cv_builderplatform.services.PersonalInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cv/personals")
public class PersonalInfoController {

    private final PersonalInfoService service;


    // /saveCv
    @PostMapping
    public ResponseEntity<PersonalInfoDTO> saveCv(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PersonalInfoDTO personalDTO) {
        PersonalInfoDTO personals =
                service.savePersonalInfoByUsername(userDetails.getUsername(),personalDTO);
        return ResponseEntity.ok(personals);
    }


    // delete
    @DeleteMapping
    public ResponseEntity<Void> deleteCv(
            @AuthenticationPrincipal UserDetails userDetails) {
        service.deletePersonalInfoByUsername(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload-profile-picture")
    public ResponseEntity<PersonalInfoDTO> uploadProfilePicture(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestParam("file") MultipartFile file){
            PersonalInfoDTO saved = service.uploadProfilePicture(userDetails.getUsername(), file);
            return ResponseEntity.ok(saved);
    }

}
