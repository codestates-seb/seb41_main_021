package com.yata.backend.domain.review.repository.Checklist;

import com.yata.backend.domain.review.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaChecklistRepository extends JpaRepository<Checklist, Long> ,ChecklistRepository {
    @Override
    Optional<Checklist> findById(Long checklistId);
}
