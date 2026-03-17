package com.example.cv_builderplatform.repositories;

import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CertificateRepository extends JpaRepository<CertificateEntity, UUID> {
    List<CertificateEntity> findByCv(CvEntity cv);
}
