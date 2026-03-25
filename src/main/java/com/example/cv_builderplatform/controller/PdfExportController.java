package com.example.cv_builderplatform.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cv_builderplatform.dto.cv.CvResponseDTO;
import com.example.cv_builderplatform.services.CvService;
import com.example.cv_builderplatform.services.PdfExportService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PdfExportController {

    private final PdfExportService pdfService;
    private final CvService cvService;
    
    @GetMapping("/cv/pdfexport")
    public ResponseEntity<byte[]> exportCvAsPdf(
        // AuthenticationPrincipal because Get Method doesnt read from @RequestBody
        @AuthenticationPrincipal UserDetails userDetail){

        CvResponseDTO cvDto = cvService.loadFullCvByUsername(userDetail.getUsername()); 
        byte[] content = pdfService.exportCv(cvDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+ userDetail.getUsername()+"_lebenslauf.pdf");
        
        return ResponseEntity.ok()
        .headers(headers)
        .contentLength(content.length)
        .contentType(MediaType.APPLICATION_PDF)
        .body(content);
    }
    
}
