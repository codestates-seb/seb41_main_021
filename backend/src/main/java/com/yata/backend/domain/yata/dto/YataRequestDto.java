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
public class YataRequestDto {
    @AllArgsConstructor
    @Getter
    @ToString
    @Builder
    public static class RequestPost {
        @NotNull(message = "제목을 입력하세요.")
        private String title;
        @NotNull(message = "특이사항을 입력해주세요.")
        private String specifics;
        @NotNull(message = "출발 시간을 입력하세요.")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss" , iso = DateTimeFormat.ISO.DATE_TIME)
        private Date departureTime;
        @NotNull(message = "도착 시간을 입력하세요.")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss" , iso = DateTimeFormat.ISO.DATE_TIME)
        private Date timeOfArrival;
        private int maxPeople;
        private int maxWatingTime;
        @Valid
        private LocationDto.Post strPoint;
        @Valid
        private LocationDto.Post destination;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class RequestResponse {
        private Long yataId;
        private Long yataRequestId;
        private YataRequest.RequestStatus yataRequestStatus;
        private YataRequest.ApprovalStatus approvalStatus;
        private String title;
        private String specifics;
        private Date departureTime;
        private Date timeOfArrival;
        private int maxPeople;
        private int maxWatingTime;
        private LocationDto.Response strPoint;
        private LocationDto.Response destination;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    @Builder
    public static class InvitationPost {
        private Long yataId;
    }

    @AllArgsConstructor
    @Getter
    @ToString
    @Builder
    public static class InvitationResponse {
        private Long yataRequestId;
        private Long yataId;
        private YataRequest.RequestStatus yataRequestStatus;
        private YataRequest.ApprovalStatus approvalStatus;
    }
}
