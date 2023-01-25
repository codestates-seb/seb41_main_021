package com.yata.backend.domain.payHistory.entity;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.yata.entity.YataMember;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PayHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payHistoryId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private Long referenceId; // 외래키

    public enum Type {
        // 추후 결제할 곳이 야타 이외에 더 생기면 추가될 수 있음
        YATA("야타");

        @Getter
        private String status;

        Type(String status) {
            this.status = status;
        }
    }
}
