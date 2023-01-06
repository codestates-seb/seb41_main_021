package com.yata.backend.domain.yata.repository;

import com.yata.backend.domain.yata.entity.YataChecklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaYataChecklistRepository extends JpaRepository<YataChecklist, Long>, YataChecklistRepository{
}
