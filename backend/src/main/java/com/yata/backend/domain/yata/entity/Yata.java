package com.yata.backend.domain.yata.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Yata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long YataId;
}
