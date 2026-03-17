package com.example.cv_builderplatform.mapper;

import com.example.cv_builderplatform.dto.cv.PersonalInfoDTO;
import com.example.cv_builderplatform.entities.subEntities.PersonalInfoEntity;
import org.springframework.stereotype.Component;

@Component()
public class PersonalInfoMapper {

    public PersonalInfoDTO mapToDTO(PersonalInfoEntity entity)  {
        PersonalInfoDTO dto = new PersonalInfoDTO();
        dto.setId(entity.getId());
        dto.setFirstname(entity.getFirstname());
        dto.setLastname(entity.getLastname());
        dto.setStreet(entity.getStreet());
        dto.setCity(entity.getCity());
        dto.setZip(entity.getZip());
        dto.setCountry(entity.getCountry());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setBirthDate(entity.getBirthDate());
        dto.setBirthplace(entity.getBirthplace());
        dto.setSummary(entity.getSummary());

        return dto;
    }

    public PersonalInfoEntity toEntity(PersonalInfoDTO dto) {
        PersonalInfoEntity entity = new PersonalInfoEntity();
        entity.setId(dto.getId());
        entity.setFirstname(dto.getFirstname());
        entity.setLastname(dto.getLastname());
        entity.setStreet(dto.getStreet());
        entity.setCity(dto.getCity());
        entity.setZip(dto.getZip());
        entity.setCountry(dto.getCountry());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setBirthDate(dto.getBirthDate());
        entity.setBirthplace(dto.getBirthplace());
        entity.setSummary(dto.getSummary());

        return entity;
    }

    public PersonalInfoEntity updateEntity(PersonalInfoEntity entity, PersonalInfoDTO dto){
        entity.setId(dto.getId());
        entity.setFirstname(dto.getFirstname());
        entity.setLastname(dto.getLastname());
        entity.setStreet(dto.getStreet());
        entity.setCity(dto.getCity());
        entity.setZip(dto.getZip());
        entity.setCountry(dto.getCountry());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setBirthDate(dto.getBirthDate());
        entity.setBirthplace(dto.getBirthplace());
        entity.setSummary(dto.getSummary());

        return entity;
    }

}
