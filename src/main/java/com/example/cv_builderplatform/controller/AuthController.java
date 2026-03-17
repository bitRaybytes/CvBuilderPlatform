package com.example.cv_builderplatform.controller;

import com.example.cv_builderplatform.dto.AuthResponseDTO;
import com.example.cv_builderplatform.dto.LoginRequestDTO;
import com.example.cv_builderplatform.dto.RegistrationRequestDTO;
import com.example.cv_builderplatform.dto.UserResponseDTO;
import com.example.cv_builderplatform.services.AuthService;
import com.example.cv_builderplatform.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody RegistrationRequestDTO request){
            UserResponseDTO response = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request){
        com.example.cv_builderplatform.dto.AuthResponseDTO dto = authService.login(request);
        AuthResponseDTO response = new AuthResponseDTO(dto.getAccessToken(), "bearer", request.getUsername());
        return ResponseEntity.ok(response);
    }

}
