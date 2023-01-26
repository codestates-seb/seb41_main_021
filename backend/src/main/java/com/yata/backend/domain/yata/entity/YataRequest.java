package com.yata.backend.domain.yata.entity;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.global.audit.Auditable;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    @Column(length = 100)
    private String title;

    @Column(length = 100)
    private String specifics;

    @Column
    private Date departureTime;

    @Column
    private Date timeOfArrival;

    @Column(length = 20, nullable = false)
    private int boardingPersonCount;

    @Column(nullable = false)
    private int maxWaitingTime;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private YataRequest.RequestStatus requestStatus;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private YataRequest.ApprovalStatus approvalStatus;

    @ManyToOne
    @JoinColumn(name = "YATA_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Yata yata;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne(cascade = CascadeType.ALL)
    private Location strPoint;

    @OneToOne(cascade = CascadeType.ALL)
     private Location destination;

    public enum RequestStatus {
        INVITE("초대"),
        APPLY("신청");

        @Getter
        private String status;

        RequestStatus(String status) {
            this.status = status;
        }
    }

    public enum ApprovalStatus {
        ACCEPTED("수락됨"),
        REJECTED("거절됨"),
        NOT_YET("승인 전");

        @Getter
        private String status;

        ApprovalStatus(String status) {
            this.status = status;
        }

    }
}
