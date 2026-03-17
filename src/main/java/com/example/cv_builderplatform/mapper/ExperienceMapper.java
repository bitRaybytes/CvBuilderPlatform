package com.example.cv_builderplatform.mapper;

import com.example.cv_builderplatform.dto.cv.ExperienceDTO;
import com.example.cv_builderplatform.entities.subEntities.ExperienceEntity;
import org.springframework.stereotype.Component;

@Component
public class ExperienceMapper {

    public ExperienceEntity toEntity (ExperienceDTO dto){
        ExperienceEntity entity = new ExperienceEntity();
        entity.setId(dto.getId());
        entity.setCompany(dto.getCompany());
        entity.setRole(dto.getRole());
        entity.setDateFrom(dto.getDateFrom());
        entity.setDateTo(dto.getDateTo());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public ExperienceDTO mapToDTO(ExperienceEntity entity){
        ExperienceDTO dto = new ExperienceDTO();
        dto.setId(entity.getId());
        dto.setCompany(entity.getCompany());
        dto.setRole(entity.getRole());
        dto.setDateFrom(entity.getDateFrom());
        dto.setDateTo(entity.getDateTo());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public ExperienceEntity updateExperienceEntity(ExperienceEntity entity, ExperienceDTO dto){
        entity.setId(dto.getId());
        entity.setCompany(dto.getCompany());
        entity.setRole(dto.getRole());
        entity.setDateFrom(dto.getDateFrom());
        entity.setDateTo(dto.getDateTo());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
