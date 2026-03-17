package com.example.cv_builderplatform.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDTO {

    private String accessToken;
    private String tokenType;
    private String username;

    public AuthResponseDTO(String accessToken){
        this.accessToken = accessToken;
    }
}
