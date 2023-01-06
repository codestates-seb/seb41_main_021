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
public class YataMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long YataMemberId;

    private enum Payment {
        PAID("지불 완료"),
        NOT_YET("지불 전");

        @Getter
        private String status;

        Payment(String status) {
            this.status = status;
        }
    }
}
