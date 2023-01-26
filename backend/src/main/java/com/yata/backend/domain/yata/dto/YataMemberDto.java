package com.yata.backend.domain.yata.dto;

import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.entity.YataRequest;
import lombok.*;

@Getter
@Setter
@ToString
public class YataMemberDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    @Builder
    public static class Response {
        private Long yataId;
        private Long yataMemberId;
        private String email;
        private String nickname;
        private boolean yataPaid;
        private Long point;
        private YataMember.GoingStatus goingStatus;

    }

//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Getter
//    @ToString
//    @Builder
//    public static class pointPaymentResponse {
//        private Long yataId;
//        private Long yataMemberId;
//        private boolean yataPaid;
//        // 지불한 가격
//        private Long point; // 잔액
//         private YataMember.GoingStatus goingStatus;
//         // createdAt // 지불한 일시
//    }
}
