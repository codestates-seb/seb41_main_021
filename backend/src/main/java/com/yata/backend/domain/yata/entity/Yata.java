package com.yata.backend.domain.yata.entity;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.global.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    //todo departureTime date타입으로 바꾸기
    @Column
    private String departureTime;

    @Column
    private String timeOfArrival;

    @Column(nullable = false,length = 50)
    private String title;

    @Column(nullable = false,length = 100)
    private String content;

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

//    @OneToOne(cascade = CascadeType.ALL)
 //       @JoinColumn(name = "YATA_ID")
//    private Location strPoint;
//
//    public void addStrPoint(Location strPoint) {
//        this.strPoint = strPoint;}
//
//    @OneToOne(cascade = CascadeType.ALL)
 //       @JoinColumn(name = "YATA_ID")
//    private Location destination;
//
//    public void addDestination(Location destination) {
//        this.destination = destination;}

//    @OneToMany(mappedBy = "yata",cascade = CascadeType.ALL)
//    private List<YataChecklist> yataChecklists = new ArrayList<>();


//    @OneToMany(mappedBy = "yata" , cascade = CascadeType.ALL)
//    private List<Location> waypoints = new ArrayList<>();
//
    @ManyToOne
    @JoinColumn(name = "EMAIL")
    private Member member;

    @OneToMany(mappedBy = "yata")
    private List<YataRequest> yataRequests = new ArrayList<>();

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
        POST_WAITING(1,"예약 전"),
        POST_RESERVED(2,"예약 완료"),
        POST_MOVING(3,"가는 중"),
        POST_CLOSED(4,"마감"),
        POST_WARNING(5,"경고"),
        POST_ACCEPTED(6,"수락"),
        POST_DENIED(7,"거절");

        @Getter
        private int statusNumber;

        @Getter
        private String status;

        PostStatus(int statusNumber, String status){this.status = status;
        this.statusNumber=statusNumber;}
    }

}
