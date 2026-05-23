package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.dto.cv.PersonalInfoDTO;
import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.PersonalInfoEntity;
import com.example.cv_builderplatform.exceptions.DataIntegrityViolationException;
import com.example.cv_builderplatform.exceptions.FileNotFoundException;
import com.example.cv_builderplatform.handler.ImageUploadHandler;
import com.example.cv_builderplatform.mapper.PersonalInfoMapper;
import com.example.cv_builderplatform.repositories.PersonalInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Service
public class PersonalInfoService {

    private final CvService cvService;
    private final PersonalInfoRepository repo;
    private final PersonalInfoMapper mapper;
    private final ImageUploadHandler picUploader;

    public PersonalInfoService(
        CvService cvService, PersonalInfoRepository repo, 
        PersonalInfoMapper personalMapper, ImageUploadHandler picUploader) {
        this.cvService = cvService;
        this.repo = repo;
        this.mapper = personalMapper;
        this.picUploader = picUploader;
    }

    public PersonalInfoDTO addPersonalInfo(
            String username, PersonalInfoDTO dto){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        PersonalInfoEntity entity = mapper.toEntity(dto);
        entity.setCv(cv);
        PersonalInfoEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }

    public PersonalInfoDTO updatePersonalInfo(
            UUID personalInfoId, PersonalInfoDTO dto){
        PersonalInfoEntity entity = repo.findById(personalInfoId).orElseThrow(()->new DataIntegrityViolationException("Persona nicht gefunden"));
        mapper.updateEntity(entity,dto);
        PersonalInfoEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }

    public PersonalInfoDTO savePersonalInfoByUsername(
            String username, PersonalInfoDTO dto) {

        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        PersonalInfoEntity personalInfo = repo.findByCv(cv).orElse(new PersonalInfoEntity());
        mapper.updateEntity(personalInfo, dto);
        personalInfo.setCv(cv);
        PersonalInfoEntity saved = repo.save(personalInfo);
        // PersonalInfoDTO savedDTO = mapper.mapToDTO(saved);
        // CvResponseDTO cvdto = new CvResponseDTO();
        // cvdto.setPersonals(savedDTO);
        return mapper.mapToDTO(saved);
    }

    // Delete ByUsername because of 1:1 relation (cv - personals)
    public void deletePersonalInfoByUsername(String username){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        repo.findByCv(cv).ifPresent(repo::delete);
    }

    public PersonalInfoDTO loadPersonalsInfoByUsername(String username){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        PersonalInfoEntity entity = repo.findByCv(cv).orElseGet(PersonalInfoEntity::new);
        return mapper.mapToDTO(entity);
    }

    public PersonalInfoDTO uploadProfilePicture(String username, MultipartFile file){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        PersonalInfoEntity entity = repo.findByCv(cv).orElseGet(PersonalInfoEntity::new);

        if (file != null && !file.isEmpty()) {
            try{
                String path = picUploader.upload(file, username);
                entity.setProfilePicturePath(path); 
            }catch (IOException e){
                throw new FileNotFoundException("Datei konnte nicht gefunden werden");
            }            
        }

        entity.setCv(cv);
        PersonalInfoEntity saved = repo.save(entity);
        return mapper.mapToDTO(saved);
    }
}
