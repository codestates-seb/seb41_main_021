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

    @Column(nullable = false, length = 100)
    private String title;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private YataRequest.RequestStatus requestStatus;

    public enum RequestStatus {
        INVITE("초대"),
        INVITED("초대 완료"),
        APPLY("신청"),
        APPLIED("신청 완료"),
        APPROVED("승인됨");

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

    public YataRequest(Long yataRequestId, String title, RequestStatus requestStatus, Yata yata, Member member) {
        YataRequestId = yataRequestId;
        this.title = title;
        this.requestStatus = requestStatus;
        this.yata = yata;
        this.member = member;
    }
}
