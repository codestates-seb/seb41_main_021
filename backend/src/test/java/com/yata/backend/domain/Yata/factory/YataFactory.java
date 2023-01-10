package com.yata.backend.domain.Yata.factory;

import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Yata;

public class YataFactory {

    public static YataDto.NeotaPost createNeotaPostDto(){

        return YataDto.NeotaPost.builder()
                .title("부산까지 같이가실 분~")
                .content("같이 노래들으면서 가요~")
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
                .amount(yata.getAmount())
                .carModel(yata.getCarModel())
                .maxPeople(yata.getMaxPeople())
                .maxWaitingTime(yata.getMaxWaitingTime())
                .build();
    }
}
