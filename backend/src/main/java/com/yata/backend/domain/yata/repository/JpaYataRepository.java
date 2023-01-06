package com.yata.backend.domain.yata.repository;

import com.yata.backend.domain.yata.entity.Yata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaYataRepository extends JpaRepository<Yata, Long>, YataRepository {
}
