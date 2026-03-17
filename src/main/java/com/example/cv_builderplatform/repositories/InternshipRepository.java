package com.example.cv_builderplatform.repositories;

import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.entities.subEntities.InternshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InternshipRepository extends JpaRepository<InternshipEntity, UUID> {
    List<InternshipEntity> findByCv(CvEntity cv);
}
