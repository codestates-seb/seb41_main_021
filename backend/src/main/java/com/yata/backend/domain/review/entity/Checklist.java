package com.yata.backend.domain.review.entity;

import com.yata.backend.domain.review.entity.ReviewChecklist;
import com.yata.backend.domain.yata.entity.YataChecklist;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Checklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checklistId;

    @Column(nullable = false)
    private String checkContent;

    @Column(nullable = false)
    private boolean checkpn;

    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL)
    private List<ReviewChecklist> reviewChecklists = new ArrayList<>();

}
