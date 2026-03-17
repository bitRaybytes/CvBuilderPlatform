package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.dto.cv.SkillDTO;
import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.SkillEntity;
import com.example.cv_builderplatform.exceptions.DataIntegrityViolationException;
import com.example.cv_builderplatform.mapper.SkillMapper;
import com.example.cv_builderplatform.repositories.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SkillService {
    private final SkillRepository repo;
    private final SkillMapper mapper;
    private final CvService cvService;

    public SkillService(SkillRepository repo, SkillMapper mapper, CvService cvService) {
        this.repo = repo;
        this.mapper = mapper;
        this.cvService = cvService;
    }

    public SkillDTO addSkill (String username, SkillDTO dto){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        SkillEntity entity = mapper.toEntity(dto);
        entity.setCv(cv);
        SkillEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }

    public SkillDTO updateSkill(UUID skillId, SkillDTO dto){
        SkillEntity entity = repo.findById(skillId).orElseThrow(()->new DataIntegrityViolationException("Fähigkeiten nicht gefunden"));
        mapper.updateSkillEntity(entity,dto);
        SkillEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }

    public void deleteSkill(UUID skillId){repo.deleteById(skillId);}

    public List<SkillDTO> loadAllByUsername(String username){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        return repo.findByCv(cv)
                .stream()
                .map(mapper::mapToDTO)
                .toList();
    }
}
