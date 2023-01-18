package com.yata.backend.domain.review.entity;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.global.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "EMAIL")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "YATA_ID")
    private Yata yata;

    @OneToMany(mappedBy = "review",cascade = CascadeType.ALL)
    private List<ReviewChecklist> reviewChecklists = new ArrayList<>();


}
