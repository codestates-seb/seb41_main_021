package com.yata.backend.domain.yata.entity;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.notify.entity.Notify;
import com.yata.backend.domain.notify.aop.proxy.NotifyInfo;
import com.yata.backend.global.audit.Auditable;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "yata")
public class YataRequest extends Auditable implements NotifyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long YataRequestId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 100)
    private String specifics;

    @Column(nullable = false)
    private Date departureTime;

    @Column(nullable = false)
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

    @Override
    public Member getReceiver() {
        return yata.getMember();
    }

    @Override
    public Long getGoUrlId() {
        return yata.getYataId();
    }

    @Override
    public Notify.NotificationType getNotificationType() {
        return Notify.NotificationType.YATA;
    }
    public void approve() {
        this.approvalStatus = ApprovalStatus.ACCEPTED;
    }
    public boolean alreadyApproved() {
        return this.approvalStatus == ApprovalStatus.ACCEPTED;
    }
    public boolean alreadyRejected() {
        return this.approvalStatus == ApprovalStatus.REJECTED;
    }
    public boolean isApply() {
        return this.requestStatus == RequestStatus.APPLY;
    }
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
