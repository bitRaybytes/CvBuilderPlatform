package com.example.cv_builderplatform.mapper;

import com.example.cv_builderplatform.dto.cv.InternshipDTO;
import com.example.cv_builderplatform.entities.subEntities.InternshipEntity;
import org.springframework.stereotype.Component;

@Component
public class InternshipMapper {

    public InternshipDTO maptoDTO(InternshipEntity entity){
        InternshipDTO dto = new InternshipDTO();
        dto.setId(entity.getId());
        dto.setCompany(entity.getCompany());
        dto.setRole(entity.getRole());
        dto.setDateFrom(entity.getDateFrom());
        dto.setDateTo(entity.getDateTo());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public InternshipEntity toEntity(InternshipDTO dto){
        InternshipEntity entity = new InternshipEntity();
        entity.setId(dto.getId());
        entity.setCompany(dto.getCompany());
        entity.setRole(dto.getRole());
        entity.setDateFrom(dto.getDateFrom());
        entity.setDateTo(dto.getDateTo());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public InternshipEntity updateInternshipEntity(InternshipEntity entity,InternshipDTO dto){
        // entity.setId(dto.getId());
        entity.setCompany(dto.getCompany());
        entity.setRole(dto.getRole());
        entity.setDateFrom(dto.getDateFrom());
        entity.setDateTo(dto.getDateTo());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
