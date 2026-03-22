package com.example.cv_builderplatform.mapper;

import com.example.cv_builderplatform.dto.cv.SignatureDTO;
import com.example.cv_builderplatform.entities.subEntities.SignatureEntity;
import org.springframework.stereotype.Component;

@Component
public class SignatureMapper {

    public SignatureDTO mapToDTO(SignatureEntity entity){
        SignatureDTO dto = new SignatureDTO();
        dto.setId(entity.getId());
        dto.setCity(entity.getCity());
        dto.setSignatureDate(entity.getSignatureDate());
        dto.setSignaturePath(entity.getSignaturePath());

        return dto;
    }

    public SignatureEntity toEntity(SignatureDTO dto){
        SignatureEntity entity = new SignatureEntity();
        // entity.setId(dto.getId());
        entity.setCity(dto.getCity());
        entity.setSignaturePath(dto.getSignaturePath());
        entity.setSignatureDate(dto.getSignatureDate());

        return entity;
    }

    public SignatureEntity updateSignatureEntity(
        SignatureEntity entity, SignatureDTO dto) {
            // entity.setId(dto.getId());
            entity.setCity(dto.getCity());
            entity.setSignatureDate(dto.getSignatureDate());
            entity.setSignaturePath(dto.getSignaturePath());

            return entity;
    }

}
