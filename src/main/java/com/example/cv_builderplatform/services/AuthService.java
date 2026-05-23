package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.dto.AuthResponseDTO;
import com.example.cv_builderplatform.dto.LoginRequestDTO;
import com.example.cv_builderplatform.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtProvider;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public AuthResponseDTO login(LoginRequestDTO login){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword()));
        String token = jwtProvider.generateToken(authentication.getName());
        return new AuthResponseDTO(token);
    }

}
