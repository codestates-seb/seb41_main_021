package com.yata.backend.domain.checklist.entity;

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
    private boolean checkPN;

    @Enumerated
    @Column(nullable = false)
    private CheckKind checkKind;

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
    // TODO 연결 후에 해요 ^^
    /*@OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL)
    private List<ReviewChecklist> reviewChecklists = new ArrayList<>();*/

}
