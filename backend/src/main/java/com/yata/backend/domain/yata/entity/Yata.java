package com.yata.backend.domain.yata.entity;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.global.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Yata extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long yataId;

    //분까지만 받고 싶은데 무슨 타입으로 받아야 할까요??
    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private LocalDateTime timeOfArrival;

    @Column(nullable = false)
    private int maxWaitingTime;


    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private YataStatus yataStatus;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private PostStatus postStatus = PostStatus.POST_WAITING;


    @Column(length = 20, nullable = false)
    private String carModel;

    @Column(nullable = false)
    private int maxPeople;

    @Column(length = 10,nullable = false)
    private long amount;

    @OneToOne(mappedBy = "yata")
    private Location strPoint;

    public void addStrPoint(Location strPoint) {
        this.strPoint = strPoint;}

    @OneToOne(mappedBy = "yata")
    private Location destination;

    public void addDestination(Location destination) {
        this.destination = destination;}

    @ManyToOne
    @JoinColumn(name = "EMAIL")
    private Member member;

    public enum YataStatus {
        YATA_NEOTA("너타"),
        YATA_NATA("나타");

        @Getter
        private String status;

        YataStatus(String status) {
            this.status = status;
        }
    }

    public enum PostStatus {
        POST_WAITING("예약 전"),
        POST_RESERVED("예약 완료"),
        POST_MOVING("가는 중"),
        POST_CLOSED("마감"),
        POST_WARNING("경고"),
        POST_ACCEPTED("수락"),
        POST_DENIED("거절");

        @Getter
        private String status;

        PostStatus(String status){this.status = status; }
    }

}
