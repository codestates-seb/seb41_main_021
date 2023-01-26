package com.yata.backend.domain.payHistory.dto;

import com.yata.backend.domain.yata.entity.YataMember;
import lombok.*;

@Getter
@Setter
@ToString
public class PayHistoryDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    @Builder
    public static class Response {
        private Long payHistoryId;
        private String email;
        private String nickname;
        // 결제 금액
        // 결제 일시
        // private Long referenceId;
        // 결제 status

    }
}
