package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.dto.cv.CertificateDTO;
import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.CertificateEntity;
import com.example.cv_builderplatform.exceptions.DataIntegrityViolationException;
import com.example.cv_builderplatform.mapper.CertificateMapper;
import com.example.cv_builderplatform.repositories.CertificateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CertificateService {

    private final CvService cvService;
    private final CertificateRepository repo;
    private final CertificateMapper mapper;

    public CertificateService(CvService cvService, CertificateRepository repo, CertificateMapper mapper) {
        this.cvService = cvService;
        this.repo = repo;
        this.mapper = mapper;
    }

    public CertificateDTO addCertificate(
            String username, CertificateDTO dto) {
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        CertificateEntity entity = mapper.toEntity(dto);
        entity.setCv(cv);
        CertificateEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }

    public CertificateDTO updatedCertificate(UUID certId, CertificateDTO dto){
        CertificateEntity entity = repo.findById(certId).orElseThrow(()->new DataIntegrityViolationException("Zertifikat nicht gefunden"));
        mapper.updateCertificateEntity(entity,dto);
        CertificateEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);

    }

    public void deleteCertificate(UUID certId){
        repo.deleteById(certId);
    }

    /**
     * Hilfsmethoden
     */

    public List<CertificateDTO> loadAllByUsername(String username){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        return repo.findByCv(cv)
                .stream()
                .map(mapper::mapToDTO)
                .toList();
    }
}
