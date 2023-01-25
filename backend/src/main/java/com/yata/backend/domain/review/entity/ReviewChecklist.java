package com.yata.backend.domain.review.entity;

import com.yata.backend.domain.yata.entity.Yata;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewChecklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewCheckId; // reviewChecklistId

    @ManyToOne
    @JoinColumn(name = "REVIEW_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Review review;

    @ManyToOne
    @JoinColumn(name = "CHECKLIST_ID")
    private Checklist checklist;


}
