package com.yata.backend.domain.payHistory.dto;

import com.yata.backend.domain.payHistory.entity.PayHistory;
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
        private Long paidPrice;
        private Long point;
        // private Long referenceId;
        private PayHistory.Type type;
        private PayHistory.Gain gain;
        private LocalDateTime createdAt;
    }
}
