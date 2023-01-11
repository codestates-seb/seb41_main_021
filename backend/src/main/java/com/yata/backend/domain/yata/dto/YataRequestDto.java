package com.yata.backend.domain.yata.dto;

import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import lombok.*;

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
        @NotNull(message = "출발 시간을 입력하세요.")
        private String departureTime;
        @NotNull(message = "도착 시간을 입력하세요.")
        private String timeOfArrival;
        private int maxWatingTime;
//        private Yata yataStatus;
        private String carModel;
        // TODO 조금 애매 ~
//        @NotNull(message = "출발지를 입력하세요.")
//        private Location strPoint;
//        @NotNull(message = "도착지를 입력하세요.")
//        private Location destination;
        private int maxPeople;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class RequestResponse {
        private Long yataRequestId;
        private String title;
        //        private List<String> checklists;
        private String departureTime;
        private String timeOfArrival;
        private int maxWatingTime;
        //        private Yata yataStatus;
        private String carModel;

//        private Location strPoint;

//        private Location destination;
        private int maxPeople;
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
