package com.example.cv_builderplatform.repositories;

import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.SkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SkillRepository extends JpaRepository<SkillEntity, UUID> {
    List<SkillEntity> findByCv(CvEntity cv);
}
