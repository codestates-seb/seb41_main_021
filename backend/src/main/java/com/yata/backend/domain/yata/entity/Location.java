package com.yata.backend.domain.yata.entity;

import lombok.*;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
// @Embeddable
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;
    @Column(nullable = false, name = "LOCATION" , columnDefinition = "GEOMETRY")
    private Point location;
    @Column(length = 100, nullable = false)
    private String address;

    @OneToOne
    @JoinColumn(name = "YATA_ID")
    private Yata yata;

    public void addYata(Yata yata) {
        this.yata = yata;
    }

}
