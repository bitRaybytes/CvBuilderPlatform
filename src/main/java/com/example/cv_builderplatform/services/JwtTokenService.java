package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.security.JwtTokenProvider;
import org.springframework.stereotype.Service;


@Service
public class JwtTokenService {
    private final JwtTokenProvider provider;

    public JwtTokenService(JwtTokenProvider provider) {
        this.provider = provider;
    }

    public String generateToken(String username) {
        return provider.generateToken(username);
    }
}
