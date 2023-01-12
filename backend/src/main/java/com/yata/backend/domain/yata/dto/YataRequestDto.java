package com.yata.backend.domain.yata.dto;

import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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
//        private List<String> checklists;
        private String content;
        @NotNull(message = "출발 시간을 입력하세요.")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss" , iso = DateTimeFormat.ISO.DATE_TIME)
        private Date departureTime;
        @NotNull(message = "도착 시간을 입력하세요.")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss" , iso = DateTimeFormat.ISO.DATE_TIME)
        private Date timeOfArrival;
        private int maxPeople;
        private int maxWatingTime;
        private String carModel;
//        @Valid
//        private LocationDto.Post strPoint;
//        @Valid
//        private LocationDto.Post destination;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class RequestResponse {
        private Long yataRequestId;
        private YataRequest.RequestStatus yataRequestStatus;
        private String title;
        private String content;
        //        private List<String> checklists;
        private Date departureTime;
        private Date timeOfArrival;
        private int maxPeople;
        private int maxWatingTime;
        private String carModel;
//        private LocationDto.Post strPoint;
//        private LocationDto.Post destination;
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
        private Long yataId;
    }
}
