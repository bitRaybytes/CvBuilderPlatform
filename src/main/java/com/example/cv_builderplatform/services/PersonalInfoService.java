package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.dto.cv.CvResponseDTO;
import com.example.cv_builderplatform.dto.cv.PersonalInfoDTO;
import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.PersonalInfoEntity;
import com.example.cv_builderplatform.exceptions.DataIntegrityViolationException;
import com.example.cv_builderplatform.mapper.PersonalInfoMapper;
import com.example.cv_builderplatform.repositories.PersonalInfoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class PersonalInfoService {

    private final CvService cvService;
    private final PersonalInfoRepository repo;
    private final PersonalInfoMapper personalMapper;

    public PersonalInfoService(CvService cvService, PersonalInfoRepository repo, PersonalInfoMapper personalMapper) {
        this.cvService = cvService;
        this.repo = repo;
        this.personalMapper = personalMapper;
    }

    public PersonalInfoDTO addPersonalInfo(
            String username, PersonalInfoDTO dto){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        PersonalInfoEntity entity = personalMapper.toEntity(dto);
        entity.setCv(cv);
        PersonalInfoEntity saved = repo.save(entity);
        return personalMapper.mapToDTO(saved);
    }

    public PersonalInfoDTO updatePersonalInfo(
            UUID personalInfoId, PersonalInfoDTO dto){
        PersonalInfoEntity entity = repo.findById(personalInfoId).orElseThrow(()->new DataIntegrityViolationException("Persona nicht gefunden"));
        personalMapper.updateEntity(entity,dto);
        PersonalInfoEntity saved = repo.save(entity);
        return personalMapper.mapToDTO(saved);
    }

    public CvResponseDTO savePersonalInfoByUsername(
            String username, PersonalInfoDTO dto) {

        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        PersonalInfoEntity personalInfo = repo.findByCv(cv).orElse(new PersonalInfoEntity());
        personalMapper.updateEntity(personalInfo, dto);
        personalInfo.setCv(cv);
        PersonalInfoEntity saved = repo.save(personalInfo);
        PersonalInfoDTO savedDTO = personalMapper.mapToDTO(saved);
        CvResponseDTO cvdto = new CvResponseDTO();
        cvdto.setPersonals(savedDTO);
        return cvdto;
    }

    // Delete ByUsername because of 1:1 relation (cv - personals)
    public void deletePersonalInfoByUsername(String username){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        repo.findByCv(cv).ifPresent(repo::delete);
    }

    public PersonalInfoDTO loadPersonalsInfoByUsername(String username){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        PersonalInfoEntity entity = repo.findByCv(cv).orElseGet(PersonalInfoEntity::new);
        return personalMapper.mapToDTO(entity);
    }
}
