package com.example.cv_builderplatform.repositories;

import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.HobbyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HobbyRepository extends JpaRepository<HobbyEntity, UUID> {
    List<HobbyEntity> findByCv(CvEntity cv);
}
