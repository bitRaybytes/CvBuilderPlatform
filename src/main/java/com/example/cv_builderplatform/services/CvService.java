package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.dto.cv.CvResponseDTO;
import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.UserEntity;
import com.example.cv_builderplatform.exceptions.CvNotFoundException;
import com.example.cv_builderplatform.exceptions.UserNotFoundException;
import com.example.cv_builderplatform.repositories.CvRepository;
import com.example.cv_builderplatform.repositories.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CvService {
    private final UserRepository userRepo;
    private final CvRepository cvRepo;

    @Lazy
    private final CvLoadService cvLoadService;


    public CvService(UserRepository userRepo, CvRepository cvRepo, @Lazy CvLoadService cvLoadService) {
        this.userRepo = userRepo;
        this.cvRepo = cvRepo;
        this.cvLoadService = cvLoadService;
    }

    public CvResponseDTO loadCv(UUID cvId) throws Exception{

        CvEntity cv = cvRepo.findById(cvId).orElseThrow(()->new CvNotFoundException("CV nicht gefunden: "+cvId));
        CvResponseDTO dto = new CvResponseDTO();
        dto.setTitle(cv.getTitle());
        return dto;
    }



    public CvResponseDTO loadFullCvByUsername(String username){
        return cvLoadService.loadFullCv(username);
    }


    public CvEntity getOrCreateCv(UserEntity user){
        return cvRepo.findByUser(user).orElseGet(
                ()-> {
                    CvEntity newCv = new CvEntity();
                    newCv.setUser(user);
                    return cvRepo.save(newCv);
                }
        );
    }

    public CvEntity getOrCreateCvByUsername(String username){
        UserEntity user = userRepo.findByUsername(username).orElseThrow(()-> new UserNotFoundException("User not found: "+ username));
        return getOrCreateCv(user);
    }

    public void deleteCv(UUID cvId){
        cvRepo.deleteById(cvId);
    }
}
