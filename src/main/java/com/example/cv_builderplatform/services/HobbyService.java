package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.dto.cv.HobbyDTO;
import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.HobbyEntity;
import com.example.cv_builderplatform.exceptions.DataIntegrityViolationException;
import com.example.cv_builderplatform.mapper.HobbyMapper;
import com.example.cv_builderplatform.repositories.HobbyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HobbyService {
    private final HobbyRepository repo;
    private final HobbyMapper mapper;
    private final CvService cvService;

    public HobbyService(HobbyRepository repo, HobbyMapper mapper, CvService cvService) {
        this.repo = repo;
        this.mapper = mapper;
        this.cvService = cvService;
    }

    /**
     * addHobby(String username, HobbyDTO dto)
     *   → cv laden
     *   → toEntity, cv setzen, speichern
     *   → mapToDTO zurückgeben
     */
    public HobbyDTO addHobby(String username, HobbyDTO dto){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        HobbyEntity entity = mapper.toEntity(dto);
        entity.setCv(cv);
        HobbyEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }


    /** updateHobby(UUID hobbyId, HobbyDTO dto)
     *   → Hobby per ID laden
     *   → updateEntity aufrufen
     *   → speichern
     *   → mapToDTO zurückgeben
     */

    public HobbyDTO updateHobby(
            UUID hobbyId, HobbyDTO dto){
        HobbyEntity entity = repo.findById(hobbyId).orElseThrow(()->new DataIntegrityViolationException("Hobby nicht gefunden"));
        mapper.updateHobbyEntity(entity,dto);
        HobbyEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }

    /**
     * deleteHobby(UUID hobbyId)
     *   → Hobby per ID laden
     *   → löschen
     */

    public void deleteHobby(UUID id){repo.deleteById(id);}


    /***
     * Hilfsmethoden
     */
    public List<HobbyDTO> loadAllByUsername(String username) {
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        return repo.findByCv(cv)
                .stream()
                .map(mapper::mapToDTO)
                .toList();
    }

}
