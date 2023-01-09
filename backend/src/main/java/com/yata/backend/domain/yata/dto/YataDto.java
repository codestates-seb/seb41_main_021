package com.yata.backend.domain.yata.dto;

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
    public static class NeotaPost{

        @NotBlank(message = "출발시간은 공백이 아니어야 합니다.")
        private Date departureTime;

        @NotBlank(message = "도착시간은 공백이 아니어야 합니다.")
        private Date timeOfArrival;

        @NotBlank(message = "최대대기시간은 공백이 아니어야 합니다.")
        private int maxWaitingTime;

        @NotBlank(message = "차종은 공백이 아니어야 합니다.")
        private int maxPeople;

        @NotBlank(message = "제시가격은 공백이 아니어야 합니다.")
        private long amount;

        //strPoint,destination

        @Valid
        @NotNull(message = "체크리스트 입력은 필수입니다.")
        private List<YataChecklistDto> yataChecklists;






    }

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class NataPost{
        @NotBlank(message = "출발시간은 공백이 아니어야 합니다.")
        private Date departureTime;

        @NotBlank(message = "도착시간은 공백이 아니어야 합니다.")
        private Date timeOfArrival;

        @NotBlank(message = "최대대기시간은 공백이 아니어야 합니다.")
        private int maxWaitingTime;

        @NotBlank(message = "차종은 공백이 아니어야 합니다.")
        private int maxPeople;

        @NotBlank(message = "제시가격은 공백이 아니어야 합니다.")
        private long amount;

        //strPoint,destination



    }



    @Getter
    @ToString
    @Builder
    public static class Patch{
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Builder
    public static class Response {
    }

}
