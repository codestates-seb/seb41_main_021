package com.yata.backend.domain.yataRequest.factory;

import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.YataRequest;

import java.util.Date;

public class YataRequestFactory {
    public static YataRequestDto.RequestPost createYataRequestPostDto() {
        LocationDto.Post strPoint = new LocationDto.Post(2.5, 2.0, "강원도 원주시");
        LocationDto.Post destination = new LocationDto.Post(2.5, 2.0, "강원도 원주시");

        return YataRequestDto.RequestPost.builder()
                .title("태워주세욥")
                .specifics("애완견을 동반하고싶어요")
                .departureTime(new Date())
                .timeOfArrival(new Date())
                .maxPeople(3)
                .maxWatingTime(10)
                .strPoint(strPoint)
                .destination(destination)
                .build();
    }

    public static YataRequestDto.RequestResponse createYataRequestResponseDto(YataRequest yataRequest) {
        LocationDto.Response strPoint = new LocationDto.Response(yataRequest.getYata().getStrPoint().getLocation().getX(),
                yataRequest.getYata().getStrPoint().getLocation().getY(),yataRequest.getYata().getStrPoint().getAddress());
        LocationDto.Response destination = new LocationDto.Response(yataRequest.getYata().getStrPoint().getLocation().getX(),
                yataRequest.getYata().getStrPoint().getLocation().getY(),yataRequest.getYata().getDestination().getAddress());

        return YataRequestDto.RequestResponse.builder()
                .yataRequestId(yataRequest.getYataRequestId())
                .yataRequestStatus(yataRequest.getRequestStatus())
                .approvalStatus(yataRequest.getApprovalStatus())
                .title(yataRequest.getTitle())
                .specifics(yataRequest.getSpecifics())
                .departureTime(yataRequest.getYata().getDepartureTime())
                .timeOfArrival(yataRequest.getYata().getTimeOfArrival())
                .maxWatingTime(yataRequest.getYata().getMaxWaitingTime())
                .strPoint(strPoint)
                .destination(destination)
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
                .yataRequestId(yataRequest.getYataRequestId())
                .yataId(yataRequest.getYata().getYataId())
                .yataRequestStatus(yataRequest.getRequestStatus())
                .approvalStatus(yataRequest.getApprovalStatus())
                .build();
    }
}
