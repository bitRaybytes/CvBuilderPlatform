package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.dto.cv.InternshipDTO;
import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.InternshipEntity;
import com.example.cv_builderplatform.exceptions.DataIntegrityViolationException;
import com.example.cv_builderplatform.mapper.InternshipMapper;
import com.example.cv_builderplatform.repositories.InternshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InternshipService {
    private final InternshipRepository repo;
    private final InternshipMapper mapper;
    private final CvService cvService;

    public InternshipService(InternshipRepository repo, InternshipMapper mapper, CvService cvService) {
        this.repo = repo;
        this.mapper = mapper;
        this.cvService = cvService;
    }

    public InternshipDTO addInternship(String username, InternshipDTO dto){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        InternshipEntity entity = mapper.toEntity(dto);
        entity.setCv(cv);
        InternshipEntity saved = repo.save(entity);
        return mapper.maptoDTO(saved);
    }

    public InternshipDTO updateInternship(UUID internshipId, InternshipDTO dto){
        InternshipEntity entity = repo.findById(internshipId).orElseThrow(()->new DataIntegrityViolationException("Praktikum nicht gefunden"));
        mapper.updateInternshipEntity(entity,dto);
        InternshipEntity saved = repo.save(entity);
        return mapper.maptoDTO(saved);
    }

    public void deleteInternship(UUID internshipId){repo.deleteById(internshipId);}

    public List<InternshipDTO> loadAllByUsername(String username){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        return repo.findByCv(cv)
                .stream()
                .map(mapper::maptoDTO)
                .toList();
    }

}


