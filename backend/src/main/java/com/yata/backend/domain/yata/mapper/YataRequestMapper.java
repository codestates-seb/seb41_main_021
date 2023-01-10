package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface YataRequestMapper {

    YataRequest yataRequestPostDtoToYataRequest(YataRequestDto.Post requestBody);
    default YataRequestDto.Response yataRequestToYataRequestResponse(YataRequest yataRequest) {
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

        YataRequestDto.Response response = new YataRequestDto.Response(
                yataRequestId, title, departureTime, timeOfArrival, maxWatingTime, carModel, maxPeople);
        return response;
    }
    List<YataRequestDto.Response> yataRequestsToYataRequestResponses(List<YataRequest> yataRequests);
}
