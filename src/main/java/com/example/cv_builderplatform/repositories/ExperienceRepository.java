package com.example.cv_builderplatform.repositories;

import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.ExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExperienceRepository extends JpaRepository<ExperienceEntity, UUID> {
    List<ExperienceEntity> findByCv(CvEntity cv);
}
