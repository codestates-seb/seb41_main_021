package com.yata.backend.domain.review.entity;

import com.yata.backend.domain.yata.entity.Yata;

import javax.persistence.*;

@Entity
public class ReviewChecklist {
    @Id
    private Long reviewCheckId; // reviewChecklistId

    @ManyToOne
    @JoinColumn(name = "REVIEW_ID")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "CHECKLIST_ID")
    private Checklist checklist;


//체크여부 확인
    @Column(nullable = false)
    private boolean checking;




}
