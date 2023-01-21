package com.yata.backend.domain.yata.entity;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.global.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "member")
public class YataMember extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long yataMemberId;

    @Column(nullable = false)
    private boolean yataPaid; // 지불 여부

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private GoingStatus goingStatus;

    @ManyToOne
    @JoinColumn(name = "YATA_ID")
    private Yata yata;

    @ManyToOne
    @JoinColumn(name = "EMAIL")
    private Member member;

    public enum GoingStatus {
        STARTED_YET("출발 전"),
        ARRIVED("도착 완료");

        @Getter
        private String status;

        GoingStatus(String status) {
            this.status = status;
        }
    }
}
