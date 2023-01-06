package com.yata.backend.domain.yata.entity;

import com.yata.backend.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class YataRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long YataRequestId;

    private enum RequestStatus {
        INVITE("초대"),
        APPLY("신청");

        @Getter
        private String status;

        RequestStatus(String status) {
            this.status = status;
        }
    }

    @ManyToOne
    @JoinColumn(name = "YATA_ID")
    private Yata yata;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
