package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.dto.YataMemberDto;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.global.utils.GeometryUtils;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface YataMapper {


    default Yata yataPostDtoToYata(YataDto.YataPost requestBody) throws ParseException {
        if (requestBody == null) {
            return null;
        }

        Yata.YataBuilder yata = Yata.builder();
        if (requestBody.getDepartureTime() != null) {
            yata.departureTime(requestBody.getDepartureTime());
        }


        if (requestBody.getTimeOfArrival() != null) {
            yata.timeOfArrival(requestBody.getTimeOfArrival());
        }
        //transFormat.parse(post.getDepartureTime())
        yata.title(requestBody.getTitle());
        yata.specifics(requestBody.getSpecifics());
        yata.maxWaitingTime(requestBody.getMaxWaitingTime());
        yata.carModel(requestBody.getCarModel());
        yata.maxPeople(requestBody.getMaxPeople());
        yata.amount(requestBody.getAmount());
        yata.strPoint(postToLocation(requestBody.getStrPoint()));
        yata.destination(postToLocation(requestBody.getDestination()));
        yata.yataStatus(requestBody.getYataStatus());
        yata.postStatus(Yata.PostStatus.POST_OPEN);

        return yata.build();
    }

    Yata yataPatchToYata(YataDto.Patch requestBody);

    default YataDto.Response yataToYataResponse(Yata yata) {
        if (yata == null) {
            return null;
        }

        YataDto.Response.ResponseBuilder response = YataDto.Response.builder();

        if (yata.getYataId() != null) {
            response.yataId(yata.getYataId());
        }
        if (yata.getYataMembers() == null) response.reservedMemberNum(0);
        else response.reservedMemberNum(yata.getYataMembers().stream().distinct().mapToInt(YataMember::getBoardingPersonCount).sum());
        response.departureTime(yata.getDepartureTime());
        response.timeOfArrival(yata.getTimeOfArrival());
        response.title(yata.getTitle());
        response.specifics(yata.getSpecifics());
        response.createdAt(yata.getCreatedAt());
        response.modifiedAt(yata.getModifiedAt());
        response.maxWaitingTime(yata.getMaxWaitingTime());
        response.maxPeople(yata.getMaxPeople());
        response.amount(yata.getAmount());
        response.carModel(yata.getCarModel());
        response.strPoint(locationToResponse(yata.getStrPoint()));
        response.destination(locationToResponse(yata.getDestination()));
        response.postStatus(yata.getPostStatus());
        response.yataStatus(yata.getYataStatus());
        response.email(yata.getMember().getEmail());
        response.nickName(yata.getMember().getNickname());
        response.fuelTank(yata.getMember().getFuelTank());
        if (yata.getYataMembers() != null) {
            response.yataMembers(yata.getYataMembers().stream()
                    .map(yataMember -> {
                        return YataMemberDto.Response.builder()
                                .yataId(yataMember.getYata().getYataId())
                                .yataMemberId(yataMember.getYataMemberId())
                                .yataPaid(yataMember.isYataPaid())
                                .nickname(yataMember.getMember().getNickname())
                                .email(yataMember.getMember().getEmail())
                                .goingStatus(yataMember.getGoingStatus())
                                .reviewReceived(yataMember.isReviewReceived())
                                .reviewWritten(yataMember.isReviewWritten())
                                .build();
                    }).collect(Collectors.toList())); //여기선 넣어주고
        }
        return response.build();
    }
    default List<YataDto.AcceptedResponse> yataToMyYatas(List<Yata> yatas , String email){
        if (yatas == null) {
            return null;
        }
        return yatas.stream().map(yata -> {
            YataDto.AcceptedResponse response = new YataDto.AcceptedResponse();
            response.setYataResponse(yataToYataResponse(yata));
            response.getYataResponse().setYataMembers(null);
            YataMember yataMember = yata.getYataMembers()
                    .stream().distinct()
                    .filter(yataMember1 ->
                            yataMember1.getMember().getEmail().equals(email)).findFirst().orElse(null);
            response.setYataMemberId(yataMember != null ? yataMember.getYataMemberId() : null);
            response.setYataPaid(yataMember != null && yataMember.isYataPaid());
            response.setGoingStatus(yataMember != null ? yataMember.getGoingStatus() : null);
            response.setReviewReceived(yataMember != null && yataMember.isReviewReceived());
            response.setReviewWritten(yataMember != null && yataMember.isReviewWritten());
            return response;
        }).collect(Collectors.toList());
    }
    default List<YataDto.Response> yatasToYataResponses(List<Yata> yatas) {
        if (yatas == null) {
            return null;
        }
        return yatas.stream()
                .map(yata -> {
                    YataDto.Response res = yataToYataResponse(yata);
                    res.setYataMembers(null);
                    return res;
                }).collect(Collectors.toList());
    }


    default Location postToLocation(LocationDto.Post post) throws ParseException {
        if (post == null) {
            return null;
        }

        Location.LocationBuilder location = Location.builder();


        // WKTReader를 통해 WKT를 실제 타입으로 변환합니다.
        Point point = GeometryUtils.getPoint(post);

        location.location(point);
        location.address(post.getAddress());


        return location.build();
    }

    default LocationDto.Response locationToResponse(Location location) {
        if (location == null) {
            return null;
        }

        LocationDto.Response.ResponseBuilder response = LocationDto.Response.builder();

        response.longitude(location.getLocation().getY());
        response.latitude(location.getLocation().getX());
        response.address(location.getAddress());

        return response.build();
    }
}
