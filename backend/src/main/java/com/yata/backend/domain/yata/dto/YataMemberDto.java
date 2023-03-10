package com.yata.backend.domain.yata.dto;

import com.yata.backend.domain.yata.entity.YataMember;
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

        private boolean reviewWritten;

        private boolean reviewReceived;
        private int boardingPersonCount;
        private YataMember.GoingStatus goingStatus;
        private String imgUrl;

    }
}
