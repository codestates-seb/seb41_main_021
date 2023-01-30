package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface YataRequestMapper {
    YataRequest yataRequestPostDtoToYataRequest(YataRequestDto.RequestPost requestBody);

    default YataRequestDto.RequestResponse yataRequestToYataRequestResponse(YataRequest yataRequest) {
        if (yataRequest == null) {
            return null;
        }
        YataRequestDto.RequestResponse.RequestResponseBuilder response = YataRequestDto.RequestResponse.builder();

        if (yataRequest.getYataRequestId() != null) {
            response.yataRequestId(yataRequest.getYataRequestId());
        }
        response.yataId(yataRequest.getYata().getYataId());
        response.amount(yataRequest.getYata().getAmount());
        response.maxPeople(yataRequest.getYata().getMaxPeople());
        response.email(yataRequest.getMember().getEmail());
        response.nickname(yataRequest.getMember().getNickname());
        response.yataOwnerEmail(yataRequest.getYata().getMember().getEmail());
        response.yataOwnerNickname(yataRequest.getYata().getMember().getNickname());
        response.imgUrl(yataRequest.getYata().getMember().getImgUrl() != null ? yataRequest.getYata().getMember().getImgUrl().getUrl() : null);
        response.yataOwnerImgUrl(yataRequest.getYata().getMember().getImgUrl() != null ? yataRequest.getYata().getMember().getImgUrl().getUrl() : null);

        response.yataRequestStatus(yataRequest.getRequestStatus());
        response.approvalStatus(yataRequest.getApprovalStatus());
        response.title(yataRequest.getTitle());
        response.specifics(yataRequest.getSpecifics());
        response.departureTime(yataRequest.getDepartureTime());
        response.timeOfArrival(yataRequest.getTimeOfArrival());
        response.boardingPersonCount(yataRequest.getBoardingPersonCount());
        response.maxWaitingTime(yataRequest.getMaxWaitingTime());
        response.strPoint(yataRequest.getStrPoint() == null ?
                locationToResponse(yataRequest.getYata().getStrPoint()) : locationToResponse(yataRequest.getStrPoint()));
        response.destination(yataRequest.getDestination() == null ?
                locationToResponse(yataRequest.getYata().getDestination()) : locationToResponse(yataRequest.getDestination()));
        response.createdAt(yataRequest.getCreatedAt());

        return response.build();
    }

    default List<YataRequestDto.RequestResponse> yataRequestsToYataRequestResponses(List<YataRequest> yataRequests) {
        if (yataRequests == null) {
            return null;
        }

        return yataRequests.stream()
                .map(this::yataRequestToYataRequestResponse).collect(Collectors.toList());
    }



    default Location postToLocation(LocationDto.Post post) throws ParseException {
        if (post == null) {
            return null;
        }

        Location.LocationBuilder location = Location.builder();
        String pointWKT = String.format("POINT(%s %s)", post.getLongitude(), post.getLatitude());

        // WKTReader를 통해 WKT를 실제 타입으로 변환합니다.
        Point point = (Point) new WKTReader().read(pointWKT);

        location.location(point);
        location.address(post.getAddress());


        return location.build();
    }

    default LocationDto.Response locationToResponse(Location location) {
        if (location == null) {
            return null;
        }

        LocationDto.Response.ResponseBuilder response = LocationDto.Response.builder();

        response.longitude(location.getLocation().getX());
        response.latitude(location.getLocation().getY());
        response.address(location.getAddress());

        return response.build();
    }
}
