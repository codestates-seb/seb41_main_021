package com.yata.backend.domain.Yata.factory;

import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;



public class YataFactory {

    public static YataDto.YataPost createYataPostDto() throws ParseException {

        return YataDto.YataPost.builder()
                .title("부산까지 같이가실 분~")
                .specifics("같이 노래들으면서 가요~")
                .departureTime(new Date())
                .timeOfArrival(new Date())
                .amount(2000L)
                .carModel("bmw")
                .maxPeople(3)
                .maxWaitingTime(20)
                .yataStatus(YataStatus.YATA_NEOTA)
                .build();
    }

    public static YataDto.Patch createYataPatchDto() throws ParseException {

        return YataDto.Patch.builder()
                .title("인천까지 같이가실 분~")
                .specifics("같이 춤추면서 가요~")
                .departureTime(new Date())
                .timeOfArrival(new Date())
                .amount(1500L)
                .carModel("porsche")
                .maxPeople(2)
                .maxWaitingTime(10)
                .build();
    }

    public static YataDto.Response createYataResponseDto(Yata yata){
        return YataDto.Response.builder()
                .yataId(yata.getYataId())
                .title(yata.getTitle())
                .specifics(yata.getSpecifics())
                .timeOfArrival(yata.getTimeOfArrival())
                .departureTime(yata.getDepartureTime())
                .amount(yata.getAmount())
                .carModel(yata.getCarModel())
                .maxPeople(yata.getMaxPeople())
                .maxWaitingTime(yata.getMaxWaitingTime())
                .yataStatus(yata.getYataStatus())
                .postStatus(yata.getPostStatus())
                .build();

    }
}
