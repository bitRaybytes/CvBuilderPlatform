package com.example.cv_builderplatform.repositories;

import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.PersonalInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfoEntity, UUID> {
    Optional<PersonalInfoEntity> findByCv(CvEntity cv);
}
