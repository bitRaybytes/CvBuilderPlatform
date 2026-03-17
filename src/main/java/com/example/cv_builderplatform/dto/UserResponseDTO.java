package com.example.cv_builderplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResponseDTO {
    private UUID uuid;
    private String username;
    private String email;
}
