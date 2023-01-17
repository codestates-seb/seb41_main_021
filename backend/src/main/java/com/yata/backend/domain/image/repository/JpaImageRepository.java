package com.yata.backend.domain.image.repository;

import com.yata.backend.domain.image.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaImageRepository extends JpaRepository<ImageEntity, UUID> {

}
