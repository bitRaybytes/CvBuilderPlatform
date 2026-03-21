package com.example.cv_builderplatform.mapper;

import com.example.cv_builderplatform.dto.cv.SkillDTO;
import com.example.cv_builderplatform.entities.subEntities.SkillEntity;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {

    public SkillEntity toEntity(SkillDTO dto){
        SkillEntity entity = new SkillEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCategory(dto.getCategory());
        entity.setLevel(dto.getLevel());
        return entity;
    }

    public SkillDTO mapToDTO(SkillEntity entity){
        SkillDTO dto = new SkillDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCategory(entity.getCategory());
        dto.setLevel(entity.getLevel());
        return dto;
    }

    public SkillEntity updateSkillEntity(SkillEntity entity, SkillDTO dto){
        // entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setLevel(dto.getLevel());
        entity.setCategory(dto.getCategory());
        return entity;
    }

}
