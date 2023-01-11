package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface YataRequestMapper {

    YataRequest yataRequestPostDtoToYataRequest(YataRequestDto.RequestPost requestBody);
    default YataRequestDto.RequestResponse yataRequestToYataRequestResponse(YataRequest yataRequest) {
        if (yataRequest == null) {
            return null;
        }
        Long yataRequestId = yataRequest.getYataRequestId();
        String title = yataRequest.getTitle();
        // TODO checklist 추가
        String departureTime = String.valueOf(yataRequest.getYata().getDepartureTime());
        String timeOfArrival = String.valueOf(yataRequest.getYata().getTimeOfArrival());
        int maxWatingTime = yataRequest.getYata().getMaxWaitingTime();
        String carModel = yataRequest.getYata().getCarModel();
        int maxPeople = yataRequest.getYata().getMaxPeople();
        // TODO 출발지 / 도착지 추가

        YataRequestDto.RequestResponse response = new YataRequestDto.RequestResponse(
                yataRequestId, title, departureTime, timeOfArrival, maxWatingTime, carModel, maxPeople);
        return response;
    }
    List<YataRequestDto.RequestResponse> yataRequestsToYataRequestResponses(List<YataRequest> yataRequests);
    YataRequest yataInvitationPostDtoToYataInvitation(YataRequestDto.InvitationPost requestBody);
    YataRequestDto.InvitationResponse yataInvitationToYataInvitationResponse(YataRequest yataRequest);
}
