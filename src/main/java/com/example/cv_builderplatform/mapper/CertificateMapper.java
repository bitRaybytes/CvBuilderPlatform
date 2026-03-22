package com.example.cv_builderplatform.mapper;

import com.example.cv_builderplatform.dto.cv.CertificateDTO;
import com.example.cv_builderplatform.entities.subEntities.CertificateEntity;
import org.springframework.stereotype.Component;

@Component
public class CertificateMapper {

    public CertificateDTO mapToDTO(CertificateEntity entity){
        CertificateDTO dto = new CertificateDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setIssuer(entity.getIssuer());
        dto.setDateIssued(entity.getDateIssued());
        return dto;
    }

    public CertificateEntity toEntity(CertificateDTO dto){
        CertificateEntity entity = new CertificateEntity();
        // entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setIssuer(dto.getIssuer());
        entity.setDateIssued(dto.getDateIssued());
        return entity;
    }

    public CertificateEntity updateCertificateEntity(CertificateEntity entity, CertificateDTO dto){
        // entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setIssuer(dto.getIssuer());
        entity.setDateIssued(dto.getDateIssued());
        return entity;
    }
}
