package com.yata.backend.domain.yataRequest.factory;

import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.YataRequest;

public class YataRequestFactory {
    public static YataRequestDto.RequestPost createYataRequestPostDto() {

        return YataRequestDto.RequestPost.builder()
                .title("태워주세욥")
//                .checklists(List.of("흡연 O","애완동물 X"))
                .departureTime("2019-09-01 23:19:45")
                .timeOfArrival("2019-09-01 23:19:45")
                .maxWatingTime(10)
//                .yataStatus()
                .carModel("lamborghini")
//                .strPoint()
//                .destination()
                .maxPeople(3)
                .build();
    }

    public static YataRequestDto.RequestResponse createYataRequestResponseDto(YataRequest yataRequest) {
        return YataRequestDto.RequestResponse.builder()
                .yataRequestId(yataRequest.getYataRequestId())
                .title(yataRequest.getTitle())
//                .checklists(List.of("흡연 O","애완동물 X"))
                .departureTime(String.valueOf(yataRequest.getYata().getDepartureTime()))
                .timeOfArrival(String.valueOf(yataRequest.getYata().getTimeOfArrival()))
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
