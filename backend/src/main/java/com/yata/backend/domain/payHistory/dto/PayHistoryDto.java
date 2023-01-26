package com.yata.backend.domain.payHistory.dto;

import com.yata.backend.domain.payHistory.entity.PayHistory;
import com.yata.backend.domain.yata.entity.YataMember;
import lombok.*;

import java.time.LocalDateTime;

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
        private Long paidPrice; // 지불한 가격
        private Long point; // 잔액
        // private Long referenceId;
        private PayHistory.Type type;
        private LocalDateTime createdAt; // 지불한 일시
    }
}
