package com.yata.backend.domain.checklist.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ChecklistEntity {
    @Id
    private Long checklistId;
}
