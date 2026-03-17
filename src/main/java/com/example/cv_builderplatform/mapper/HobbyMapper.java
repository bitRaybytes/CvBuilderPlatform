package com.example.cv_builderplatform.mapper;

import com.example.cv_builderplatform.dto.cv.HobbyDTO;
import com.example.cv_builderplatform.entities.subEntities.HobbyEntity;
import org.springframework.stereotype.Component;

@Component
public class HobbyMapper {

    public HobbyEntity toEntity (HobbyDTO dto){
        HobbyEntity entity = new HobbyEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public HobbyEntity updateHobbyEntity(HobbyEntity entity, HobbyDTO dto){
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public HobbyDTO mapToDTO(HobbyEntity entity){
        HobbyDTO dto = new HobbyDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
