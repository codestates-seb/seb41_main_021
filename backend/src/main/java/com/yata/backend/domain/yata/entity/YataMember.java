package com.yata.backend.domain.yata.entity;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.global.audit.Auditable;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "member")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class YataMember extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long yataMemberId;

    @Column(nullable = false)
    private boolean yataPaid; // 지불 여부

    @Column
    private boolean reviewWritten; // 리뷰 작성 여부

    // 리뷰 당햇는지
    @Column
    private boolean reviewReceived;

    @Column(length = 20, nullable = false)
    private int boardingPersonCount;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private GoingStatus goingStatus;

    @ManyToOne
    @JoinColumn(name = "YATA_ID")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Yata yata;

    @ManyToOne
    @JoinColumn(name = "EMAIL")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Member member;

    public void arrivedAndPaid(){
        this.setGoingStatus(GoingStatus.ARRIVED);
        this.setYataPaid(true);
    }

    public YataMember(YataRequest yataRequest){
        this.setYata(yataRequest.getYata());
        this.setMember(yataRequest.getMember());
        this.setYataPaid(false); //지불 상태 set
        this.setGoingStatus(YataMember.GoingStatus.STARTED_YET); //카풀 현황 set
        this.setBoardingPersonCount(yataRequest.getBoardingPersonCount());
    }
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
