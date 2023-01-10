package com.yata.backend.domain.yata.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataChecklist;
import lombok.*;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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

        @NotBlank(message = "출발시간은 공백이 아니어야 합니다.")
        private String departureTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
        @NotBlank(message = "도착시간은 공백이 아니어야 합니다.")
        private String timeOfArrival;

        @NotNull
        private int maxWaitingTime;

        @NotNull
        private int maxPeople;

        @NotNull
        private long amount;

        @NotBlank
        private String carModel;

        //        @Valid
//        @NotNull
//        private LocationDto.Post strPoint;
//
//        @Valid
//        @NotNull
//        private LocationDto.Post destination;

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
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String departureTime;
        private String timeOfArrival;
        private String title;
        private String content;
        private int maxWaitingTime;
        private int maxPeople;
        private long amount;
        private String carModel;

    }

}
