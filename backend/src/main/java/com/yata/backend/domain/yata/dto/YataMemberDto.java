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
        private boolean yataPaid;
        private YataMember.GoingStatus goingStatus;

    }
}
