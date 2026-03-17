package com.example.cv_builderplatform.controller;

import com.example.cv_builderplatform.dto.cv.CvResponseDTO;
import com.example.cv_builderplatform.dto.cv.PersonalInfoDTO;
import com.example.cv_builderplatform.services.PersonalInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cv/personals")
public class PersonalInfoController {

    private final PersonalInfoService personalInfos;


    // /saveCv
    @PostMapping
    public ResponseEntity<CvResponseDTO> saveCv(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PersonalInfoDTO personalDTO) {
        CvResponseDTO personals =
                personalInfos.savePersonalInfoByUsername(userDetails.getUsername(),personalDTO);
        return ResponseEntity.ok(personals);
    }


    // delete
    @DeleteMapping
    public ResponseEntity<Void> deleteCv(
            @AuthenticationPrincipal UserDetails userDetails) {
        personalInfos.deletePersonalInfoByUsername(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

}
