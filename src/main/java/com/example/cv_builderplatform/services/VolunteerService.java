package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.dto.cv.VolunteerDTO;
import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.VolunteerEntity;
import com.example.cv_builderplatform.exceptions.DataIntegrityViolationException;
import com.example.cv_builderplatform.mapper.VolunteerMapper;
import com.example.cv_builderplatform.repositories.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VolunteerService {
    private final VolunteerRepository repo;
    private final VolunteerMapper mapper;
    private final CvService cvService;

    public VolunteerService(VolunteerRepository repo, VolunteerMapper mapper, CvService cvService) {
        this.repo = repo;
        this.mapper = mapper;
        this.cvService = cvService;
    }

    public VolunteerDTO addVolunteer(String username, VolunteerDTO dto){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        VolunteerEntity entity = mapper.toEntity(dto);
        entity.setCv(cv);
        VolunteerEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }

    public VolunteerDTO updateVolunteer(UUID volunteerId, VolunteerDTO dto){
        VolunteerEntity entity = repo.findById(volunteerId).orElseThrow(()->new DataIntegrityViolationException("Ehrenamt nicht gefunden"));
        mapper.updateVolunteerEntity(entity,dto);
        VolunteerEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }

    public void deleteVolunteer(UUID volunteerId){repo.deleteById(volunteerId);}

    public List<VolunteerDTO> loadAllByUsername (String username){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        return repo.findByCv(cv)
                .stream()
                .map(mapper::mapToDTO)
                .toList();
    }

}
