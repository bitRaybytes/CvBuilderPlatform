package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.dto.cv.EducationDTO;
import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.EducationEntity;
import com.example.cv_builderplatform.exceptions.DataIntegrityViolationException;
import com.example.cv_builderplatform.mapper.EducationMapper;
import com.example.cv_builderplatform.repositories.EducationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EducationService {

    private final EducationRepository repo;
    private final EducationMapper mapper;
    private final CvService cvService;


    public EducationService(EducationRepository repo, EducationMapper mapper, CvService cvService) {
        this.repo = repo;
        this.mapper = mapper;
        this.cvService = cvService;
    }

    public EducationDTO addEducation(
            String username, EducationDTO dto) {
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        EducationEntity entity = mapper.toEntity(dto);
        entity.setCv(cv);
        EducationEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }


    public EducationDTO updateEducation(
            UUID eduId, EducationDTO dto){
        EducationEntity entity = repo.findById(eduId).orElseThrow(()->new DataIntegrityViolationException("Ausbildung nicht gefunden"));
        mapper.updateEducationEntity(entity,dto);
        EducationEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }

    public void deleteEducation(UUID eduId){
        repo.deleteById(eduId);
    }

    public List<EducationDTO> loadAllByUsername(String username){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        return repo.findByCv(cv)
                .stream()
                .map(mapper::mapToDTO)
                .toList();
    }
}
