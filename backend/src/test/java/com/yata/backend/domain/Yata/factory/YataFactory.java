package com.yata.backend.domain.Yata.factory;

import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Yata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class YataFactory {

    public static YataDto.YataPost createNeotaPostDto() throws ParseException {

        // 문자열
        String dateStr = "2021년 06월 19일 21시 05분 07초";
        // 포맷터
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
        // 문자열 -> Date
        //Date date = formatter.parse(dateStr);

        return YataDto.YataPost.builder()
                .title("부산까지 같이가실 분~")
                .content("같이 노래들으면서 가요~")
                .departureTime(String.valueOf(new Date(System.currentTimeMillis())))
                .timeOfArrival(String.valueOf(new Date(System.currentTimeMillis())))
                .amount(2000)
                .carModel("bmw")
                .maxPeople(3)
                .maxWaitingTime(20)
                .build();
    }

    public static YataDto.Response createYataResponseDto(Yata yata){
        return YataDto.Response.builder()
                .title(yata.getTitle())
                .content(yata.getContent())
                .timeOfArrival(yata.getTimeOfArrival())
                .departureTime(yata.getDepartureTime())
                .amount(yata.getAmount())
                .carModel(yata.getCarModel())
                .maxPeople(yata.getMaxPeople())
                .maxWaitingTime(yata.getMaxWaitingTime())
                .build();
    }
}
