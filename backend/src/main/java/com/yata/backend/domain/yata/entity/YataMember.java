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
    private Long YataMemberId;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private YataMember.Payment payment;

    @ManyToOne
    @JoinColumn(name = "YATA_ID")
    private Yata yata;

    @ManyToOne
    @JoinColumn(name = "EMAIL")
    private Member member;

    public enum Payment {
        PAID("지불 완료"),
        NOT_YET("지불 전");

        @Getter
        private String status;

        Payment(String status) {
            this.status = status;
        }
    }
}
