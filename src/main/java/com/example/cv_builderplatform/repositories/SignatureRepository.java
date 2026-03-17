package com.example.cv_builderplatform.repositories;

import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.SignatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SignatureRepository extends JpaRepository<SignatureEntity, UUID> {
    Optional<SignatureEntity> findByCv(CvEntity cv);
}
