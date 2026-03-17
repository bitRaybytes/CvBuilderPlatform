package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.dto.AuthResponseDTO;
import com.example.cv_builderplatform.dto.LoginRequestDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    /**
     * Authentication class for login and JWT
     *
     * */

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO login(LoginRequestDTO login){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword()));
        String token = jwtService.generateToken(authentication.getName());
        return new AuthResponseDTO(token);
    }

}
