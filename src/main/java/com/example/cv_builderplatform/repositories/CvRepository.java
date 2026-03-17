package com.example.cv_builderplatform.repositories;

import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CvRepository extends JpaRepository<CvEntity, UUID> {
    Optional<CvEntity> findByUser(UserEntity user);
}
