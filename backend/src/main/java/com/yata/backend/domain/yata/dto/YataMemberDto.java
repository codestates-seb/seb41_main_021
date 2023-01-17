package com.yata.backend.domain.yata.dto;

import com.yata.backend.domain.yata.entity.YataRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class YataMemberDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    @Builder
    public static class Post {
        private Long yataMemberId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    @Builder
    public static class Response {
        private Long yataId;
        private Long yataRequestId;
        private Long yataMemberId;
        private YataRequest.ApprovalStatus approvalStatus;
    }
}
