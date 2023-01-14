package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface YataRequestMapper {

    YataRequest yataRequestPostDtoToYataRequest(YataRequestDto.RequestPost requestBody);
    default YataRequestDto.RequestResponse yataRequestToYataRequestResponse(YataRequest yataRequest) {
        if (yataRequest == null) {
            return null;
        }
        Long yataRequestId = yataRequest.getYataRequestId();
        YataRequest.RequestStatus yataRequestStatus = yataRequest.getRequestStatus();
        String title = yataRequest.getTitle();
        String specifics = yataRequest.getSpecifics();
        Date departureTime = yataRequest.getYata().getDepartureTime();
        Date timeOfArrival = yataRequest.getYata().getTimeOfArrival();
        int maxPeople = yataRequest.getYata().getMaxPeople();
        int maxWatingTime = yataRequest.getYata().getMaxWaitingTime();

        LocationDto.Response strPoint = new LocationDto.Response(yataRequest.getYata().getStrPoint().getLatitude(),
                yataRequest.getYata().getStrPoint().getLongitude(),yataRequest.getYata().getStrPoint().getAddress());

        LocationDto.Response destination = new LocationDto.Response(yataRequest.getYata().getDestination().getLatitude(),
                yataRequest.getYata().getDestination().getLongitude(),yataRequest.getYata().getDestination().getAddress());

        YataRequestDto.RequestResponse response = new YataRequestDto.RequestResponse(
                yataRequestId, yataRequestStatus, title, specifics, departureTime, timeOfArrival, maxPeople, maxWatingTime, strPoint ,destination);

        return response;
    }
    default Slice<YataRequestDto.RequestResponse> yataRequestsToYataRequestResponses(Slice<YataRequest> yataRequests) {
        if (yataRequests == null) {
            return null;
        }
        List<YataRequestDto.RequestResponse> requestResponses = yataRequests.getContent().stream()
                .map(yataRequest -> {
                    if(yataRequest.getYata() != null){
                        LocationDto.Response strPoint = new LocationDto.Response(yataRequest.getYata().getStrPoint().getLatitude(),
                                yataRequest.getYata().getStrPoint().getLongitude(),yataRequest.getYata().getStrPoint().getAddress());

                        LocationDto.Response destination = new LocationDto.Response(yataRequest.getYata().getDestination().getLatitude(),
                                yataRequest.getYata().getDestination().getLongitude(),yataRequest.getYata().getDestination().getAddress());

                        return new YataRequestDto.RequestResponse(
                                yataRequest.getYataRequestId(),
                                yataRequest.getRequestStatus(),
                                yataRequest.getTitle(),
                                yataRequest.getSpecifics(),
                                yataRequest.getYata().getDepartureTime(),
                                yataRequest.getYata().getTimeOfArrival(),
                                yataRequest.getYata().getMaxPeople(),
                                yataRequest.getYata().getMaxWaitingTime(),
                                strPoint,
                                destination);
                    }
                    return null;
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new SliceImpl<>(requestResponses);
    }
    YataRequest yataInvitationPostDtoToYataInvitation(YataRequestDto.InvitationPost requestBody);
    default YataRequestDto.InvitationResponse yataInvitationToYataInvitationResponse(YataRequest yataRequest) {
        if (yataRequest == null) {
            return null;
        }
        Long yataRequestId = yataRequest.getYataRequestId();
        Long yataId = yataRequest.getYata().getYataId();

        YataRequestDto.InvitationResponse response = new YataRequestDto.InvitationResponse(yataRequestId, yataId);
        return response;
    }
}
