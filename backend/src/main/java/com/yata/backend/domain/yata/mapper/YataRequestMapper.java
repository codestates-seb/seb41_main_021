package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.stream.Collectors;

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
    default Slice<YataRequestDto.RequestResponse> yataRequestsToYataRequestResponses(Slice<YataRequest> yataRequests) {
        List<YataRequestDto.RequestResponse> requestResponses = yataRequests.getContent().stream()
                .map(yataRequest -> {
                    YataRequestDto.RequestResponse requestResponse = new YataRequestDto.RequestResponse(
                            yataRequest.getYataRequestId(),
                            yataRequest.getTitle(),
                            yataRequest.getYata().getDepartureTime(),
                            yataRequest.getYata().getTimeOfArrival(),
                            yataRequest.getYata().getMaxWaitingTime(),
                            yataRequest.getYata().getCarModel(),
                            yataRequest.getYata().getMaxPeople());
                    requestResponse.setYataRequestId(requestResponse.getYataRequestId());
                    requestResponse.setTitle(requestResponse.getTitle());
                    requestResponse.setDepartureTime(requestResponse.getDepartureTime());
                    requestResponse.setTimeOfArrival(requestResponse.getTimeOfArrival());
                    requestResponse.setMaxWatingTime(requestResponse.getMaxWatingTime());
                    requestResponse.setCarModel(requestResponse.getCarModel());
                    requestResponse.setMaxPeople(requestResponse.getMaxPeople());
                    return requestResponse;
                })
                .collect(Collectors.toList());
        return new SliceImpl<>(requestResponses);
    }
    YataRequest yataInvitationPostDtoToYataInvitation(YataRequestDto.InvitationPost requestBody);
    YataRequestDto.InvitationResponse yataInvitationToYataInvitationResponse(YataRequest yataRequest);
}
