package com.yata.backend.domain.yata.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataChecklist;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class YataDto {

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class YataPost{

        @NotBlank
        private String title;

        @NotBlank
        private String content;
        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss" , iso = DateTimeFormat.ISO.DATE_TIME)
        private Date departureTime;
        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss" , iso = DateTimeFormat.ISO.DATE_TIME)
        private Date timeOfArrival;

        @NotNull
        private int maxWaitingTime;

        @NotNull
        private int maxPeople;

        @NotNull
        private long amount;

        @NotBlank
        private String carModel;

        @Valid
        private LocationDto.Post strPoint;

        @Valid
        private LocationDto.Post destination;

       //todo 체크리스트 생성 후 채우기
//        @Valid
//        @NotNull(message = "체크리스트 입력은 필수입니다.")
//        private List<YataChecklistDto> plusCheckList;
//        private List<YataChecklistDto> minusCheckList;
//

    }




    @Getter
    @ToString
    @Builder
    public static class Patch{

        private String title;

        private String content;

        private String departureTime;

        private String timeOfArrival;

        private int maxWaitingTime;

        private int maxPeople;

        private long amount;

        private String carModel;

        @Valid
        private LocationDto.Post strPoint;

        @Valid
        private LocationDto.Post destination;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd'T'HH:mm:ss" , timezone = "Asia/Seoul")
        private Date departureTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd'T'HH:mm:ss" , timezone = "Asia/Seoul")
        private Date timeOfArrival;
        private String title;
        private String content;
        private int maxWaitingTime;
        private int maxPeople;
        private long amount;
        private String carModel;
        private LocationDto.Response strPoint;
        private LocationDto.Response destination;

    }

}
