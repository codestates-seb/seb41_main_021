package com.yata.backend.domain.checklist.entity;

import com.yata.backend.domain.review.entity.ReviewChecklistEntity;
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
    private boolean checkPN;

    private enum CheckKind {
        REVIEW("리뷰"),
        NEOTA("운전자"),
        NATA("탑승자");

        @Getter
        private String status;

        CheckKind(String status) {
            this.status = status;
        }
    }

    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL)
    private List<YataChecklist> yataChecklists = new ArrayList<>();

    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL)
    private List<ReviewChecklistEntity> reviewChecklists = new ArrayList<>();
}
