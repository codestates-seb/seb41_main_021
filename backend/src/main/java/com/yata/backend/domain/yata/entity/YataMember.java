package com.yata.backend.domain.yata.entity;

import com.yata.backend.global.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class YataMember extends Auditable {
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
