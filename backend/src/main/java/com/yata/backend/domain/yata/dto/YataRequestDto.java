package com.yata.backend.domain.yata.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.entity.YataRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
        @NotNull(message = "탑승 인원을 입력해주세요.")
        private Integer boardingPersonCount;
        private int maxWaitingTime;
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
        @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd'T'HH:mm:ss" , timezone = "Asia/Seoul")
        private Date departureTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd'T'HH:mm:ss" , timezone = "Asia/Seoul")
        private Date timeOfArrival;
        private int boardingPersonCount;
        private int maxWaitingTime;
        private LocationDto.Response strPoint;
        private LocationDto.Response destination;
        private LocalDateTime createdAt;
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
        private LocalDateTime createdAt;
    }
}
