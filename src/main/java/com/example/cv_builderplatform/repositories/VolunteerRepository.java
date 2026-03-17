package com.example.cv_builderplatform.repositories;

import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.VolunteerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VolunteerRepository extends JpaRepository<VolunteerEntity, UUID> {
    List<VolunteerEntity> findByCv(CvEntity cv);
}
