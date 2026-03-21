package com.example.cv_builderplatform.mapper;

import com.example.cv_builderplatform.dto.cv.EducationDTO;
import com.example.cv_builderplatform.entities.subEntities.EducationEntity;
import org.springframework.stereotype.Component;

@Component
public class EducationMapper {
    public EducationDTO mapToDTO(EducationEntity entity){
        EducationDTO dto = new EducationDTO();
        dto.setId(entity.getId());
        dto.setDateFrom(entity.getDateFrom());
        dto.setDegree(entity.getDegree());
        dto.setInstitution(entity.getInstitution());
        dto.setFieldOfStudy(entity.getFieldOfStudy());
        dto.setDateTo(entity.getDateTo());
        return dto;
    }

    public EducationEntity toEntity(EducationDTO dto){
        EducationEntity entity = new EducationEntity();
        entity.setId(dto.getId());
        entity.setDegree(dto.getDegree());
        entity.setInstitution(dto.getInstitution());
        entity.setDateTo(dto.getDateTo());
        entity.setFieldOfStudy(dto.getFieldOfStudy());
        entity.setDateFrom(dto.getDateFrom());
        return entity;
    }

    public EducationEntity updateEducationEntity(EducationEntity entity, EducationDTO dto){
        // entity.setId(dto.getId());
        entity.setFieldOfStudy(dto.getFieldOfStudy());
        entity.setDegree(dto.getDegree());
        entity.setDateFrom(dto.getDateFrom());
        entity.setDateTo(dto.getDateTo());
        entity.setInstitution(dto.getInstitution());
        return entity;
    }
}
