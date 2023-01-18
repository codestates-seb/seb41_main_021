package com.yata.backend.domain.review.entity;

import com.yata.backend.domain.yata.entity.Yata;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewChecklist {
    @Id
    private Long reviewCheckId; // reviewChecklistId

    @ManyToOne
    @JoinColumn(name = "REVIEW_ID")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "CHECKLIST_ID")
    private Checklist checklist;


}
