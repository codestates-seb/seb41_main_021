package com.yata.backend.domain.yata.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double latitude;

    @Column(length = 100,nullable = false)
    private String address;

    @OneToOne
    @JoinColumn(name = "YATA_ID")//Location 테이블에서 사용할 외래키명
    private Yata yata;

    public void addYata(Yata yata) {
        this.yata = yata;
    }

}
