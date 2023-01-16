package com.yata.backend.domain.yata.entity;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.global.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "yata")
public class YataRequest extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long YataRequestId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 100)
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private YataRequest.RequestStatus requestStatus;

    @ManyToOne
    @JoinColumn(name = "YATA_ID")
    private Yata yata;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

//    @Embedded
//    private YataDto.YataPost strPoint;
//    @Embedded
//     private YataDto.YataPost destination;

    public enum RequestStatus {
        INVITE("초대"),
        APPLY("신청");

        @Getter
        private String status;

        RequestStatus(String status) {
            this.status = status;
        }
    }

    public static YataRequest create(YataRequest yataRequest, Member member, Yata yata) {
        return YataRequest.builder()
                .title(yataRequest.getTitle())
                .content(yataRequest.getContent())
                .requestStatus(yataRequest.getRequestStatus())
                .member(member)
                .yata(yata)
                .build();
    }

}
