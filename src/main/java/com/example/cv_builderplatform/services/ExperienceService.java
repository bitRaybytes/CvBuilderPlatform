package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.dto.cv.ExperienceDTO;
import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.ExperienceEntity;
import com.example.cv_builderplatform.exceptions.DataIntegrityViolationException;
import com.example.cv_builderplatform.mapper.ExperienceMapper;
import com.example.cv_builderplatform.repositories.ExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExperienceService {

    private final CvService cvService;
    private final ExperienceRepository repo;
    private final ExperienceMapper mapper;

    public ExperienceService(CvService cvService, ExperienceRepository repo, ExperienceMapper mapper) {
        this.cvService = cvService;
        this.repo = repo;
        this.mapper = mapper;
    }

    public ExperienceDTO addExperience(
            String username, ExperienceDTO dto){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        ExperienceEntity entity = mapper.toEntity(dto);
        entity.setCv(cv);
        ExperienceEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }

    public ExperienceDTO updateExperience (
            UUID experienceId, ExperienceDTO dto){
        ExperienceEntity entity = repo.findById(experienceId).orElseThrow(()->new DataIntegrityViolationException("Berufserfahrung nicht gefunden"));
        mapper.updateExperienceEntity(entity,dto);
        ExperienceEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }

    public void deleteExperience(UUID id){
        repo.deleteById(id);
    }

    public List<ExperienceDTO> loadAllByUsername(String username){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        return repo.findByCv(cv)
                .stream()
                .map(mapper::mapToDTO)
                .toList();
    }
}
