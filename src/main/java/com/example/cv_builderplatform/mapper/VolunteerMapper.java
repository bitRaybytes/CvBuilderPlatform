package com.example.cv_builderplatform.mapper;

import com.example.cv_builderplatform.dto.cv.VolunteerDTO;
import com.example.cv_builderplatform.entities.subEntities.VolunteerEntity;
import org.springframework.stereotype.Component;

@Component
public class VolunteerMapper {

    public VolunteerDTO mapToDTO (VolunteerEntity entity){
        VolunteerDTO dto = new VolunteerDTO();
        dto.setId(entity.getId());
        dto.setDateFrom(entity.getDateFrom());
        dto.setRole(entity.getRole());
        dto.setOrganization(entity.getOrganization());
        dto.setDescription(entity.getDescription());
        dto.setDateTo(entity.getDateTo());
        return dto;
    }

    public VolunteerEntity toEntity (VolunteerDTO dto){
        VolunteerEntity entity = new VolunteerEntity();
        entity.setId(dto.getId());
        entity.setRole(dto.getRole());
        entity.setDescription(dto.getDescription());
        entity.setDateTo(dto.getDateTo());
        entity.setDateFrom(dto.getDateFrom());
        entity.setOrganization(dto.getOrganization());
        return entity;
    }

    public VolunteerEntity updateVolunteerEntity(VolunteerEntity entity, VolunteerDTO dto){
        entity.setId(dto.getId());
        entity.setOrganization(dto.getOrganization());
        entity.setRole(dto.getRole());
        entity.setDateFrom(dto.getDateFrom());
        entity.setDateTo(dto.getDateTo());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
