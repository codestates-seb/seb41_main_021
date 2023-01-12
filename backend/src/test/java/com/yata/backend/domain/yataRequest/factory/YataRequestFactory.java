package com.yata.backend.domain.yataRequest.factory;

import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.YataRequest;

import java.util.Date;

public class YataRequestFactory {
    public static YataRequestDto.RequestPost createYataRequestPostDto() {

        return YataRequestDto.RequestPost.builder()
                .title("태워주세욥")
                .content("헬로~")
//                .checklists(List.of("흡연 O","애완동물 X"))
                .departureTime(new Date())
                .timeOfArrival(new Date())
                .maxPeople(3)
                .maxWatingTime(10)
                .carModel("lamborghini")
                .build();
    }

    public static YataRequestDto.RequestResponse createYataRequestResponseDto(YataRequest yataRequest) {
        return YataRequestDto.RequestResponse.builder()
                .yataRequestId(yataRequest.getYataRequestId())
                .yataRequestStatus(yataRequest.getRequestStatus())
                .title(yataRequest.getTitle())
                .content(yataRequest.getContent())
//                .checklists(List.of("흡연 O","애완동물 X"))
                .departureTime(yataRequest.getYata().getDepartureTime())
                .timeOfArrival(yataRequest.getYata().getTimeOfArrival())
                .maxWatingTime(yataRequest.getYata().getMaxWaitingTime())
//                .yataStatus()
                .carModel(yataRequest.getYata().getCarModel())
//                .strPoint()
//                .destination()
                .maxPeople(yataRequest.getYata().getMaxPeople())
                .build();
    }

    public static YataRequestDto.InvitationPost createYataInvitationPostDto() {

        return YataRequestDto.InvitationPost.builder()
                .yataId(1L)
                .build();
    }

    public static YataRequestDto.InvitationResponse createYataInvitationResponseDto(YataRequest yataRequest) {
        return YataRequestDto.InvitationResponse.builder()
                .yataId(yataRequest.getYata().getYataId())
                .build();
    }
}
