package com.example.cv_builderplatform.repositories;

import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EducationRepository extends JpaRepository<EducationEntity, UUID> {
    List<EducationEntity> findByCv(CvEntity cv);
}
