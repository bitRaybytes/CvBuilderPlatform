package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.dto.cv.SignatureDTO;
import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.SignatureEntity;
import com.example.cv_builderplatform.exceptions.DataIntegrityViolationException;
import com.example.cv_builderplatform.mapper.SignatureMapper;
import com.example.cv_builderplatform.repositories.SignatureRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SignatureService {
    private final CvService cvService;
    private final SignatureMapper mapper;
    private final SignatureRepository repo;

    public SignatureService(
            CvService cvService,
            SignatureMapper mapper,
            SignatureRepository repo) {
        this.cvService = cvService;
        this.mapper = mapper;
        this.repo = repo;
    }

    public SignatureDTO addSignature(
            String username, SignatureDTO dto){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        SignatureEntity entity = mapper.toEntity(dto);
        entity.setCv(cv);
        SignatureEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }

    public SignatureDTO updateSignature(
            UUID signatureId, SignatureDTO dto){
        SignatureEntity entity = repo.findById(signatureId).orElseThrow(()->new DataIntegrityViolationException("Signatur nicht gefunden"));
        mapper.updateSignatureEntity(entity,dto);
        SignatureEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }

    public void deleteSignature(UUID signatureId){
        repo.deleteById(signatureId);
    }

    public SignatureDTO loadByUsername (String username){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        SignatureEntity entity = repo.findByCv(cv).orElseGet(SignatureEntity::new);
        return mapper.mapToDTO(entity);
    }
}
