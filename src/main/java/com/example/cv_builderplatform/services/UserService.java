package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.dto.RegistrationRequestDTO;
import com.example.cv_builderplatform.dto.UserResponseDTO;
import com.example.cv_builderplatform.entities.UserEntity;
import com.example.cv_builderplatform.exceptions.UserNotFoundException;
import com.example.cv_builderplatform.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService {

    private final UserRepository usersRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository usersRepo, PasswordEncoder passwordEncoder){
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
    }

    //  TODO: RateLimiting, Email Verification, Captcha

    public UserResponseDTO createUser(RegistrationRequestDTO request) {

        if(usersRepo.findByUsername(request.getUsername()).isPresent() || usersRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User or email already exist");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(hashedPassword);

        usersRepo.save(user);
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public UserResponseDTO getUser(String username){
        return usersRepo.findByUsername(username)
                .map(user -> 
                    new UserResponseDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()))
                .orElseThrow( ()->new UserNotFoundException("Zertifikat nicht gefunden"));
    }

}
