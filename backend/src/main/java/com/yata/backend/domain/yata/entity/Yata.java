package com.yata.backend.domain.yata.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@ToString(exclude = "yataMembers")
@Table(indexes = {
        @Index(name = "idx_yata_yataId", columnList = "yataId", unique = true),
        @Index(name = "idx_yata_member", columnList = "EMAIL"),
        @Index(name = "idx_yata_memberAndYata", columnList = "EMAIL,yataId", unique = true),
})
@Cacheable
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "yataId")
public class Yata extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long yataId;

    @Column(nullable = false, length = 50)
    private Date departureTime;

    @Column(nullable = false, length = 50)
    private Date timeOfArrival;

    @Column(nullable = false, length = 50)
    private String title;

    @Column
    private String specifics;

    @Column(nullable = false)
    private Integer maxWaitingTime;


    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private YataStatus yataStatus;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private PostStatus postStatus;


    @Column(length = 20, nullable = false)
    private String carModel;

    @Column(nullable = false)
    private Integer maxPeople;

    @Column(length = 10, nullable = false)
    private Long amount;

    @OneToOne(cascade = CascadeType.ALL)
    private Location strPoint;

    @OneToOne(cascade = CascadeType.ALL)
    private Location destination;


    @ManyToOne
    @JoinColumn(name = "EMAIL")
    private Member member;
    @org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "yata", cascade = CascadeType.REMOVE , fetch = FetchType.LAZY)
    private List<YataMember> yataMembers = new ArrayList<>();
    public long getArrivalTimeByLong() {
        return timeOfArrival.getTime();
    }
    public long getDepartureTimeByLong() {
        return departureTime.getTime();
    }
    public enum PostStatus {
        POST_OPEN(1, "신청가능"),
        POST_CLOSED(2, "마감");

        @Getter
        private int statusNumber;

        @Getter
        private String status;

        PostStatus(int statusNumber, String status) {
            this.status = status;
            this.statusNumber = statusNumber;
        }
    }

    //테스트 위한 생성자
    public Yata(long yataId, List<YataMember> yataMembers) {
        this.yataId = yataId;
        this.yataMembers = yataMembers;
    }

    public Yata(long yataId) {
        this.yataId = yataId;
    }

}