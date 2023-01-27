package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.dto.YataMemberDto;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.global.utils.GeometryUtils;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface YataMapper {
//    Yata yataPostDtoToYata(YataDto.YataPost requestBody);

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
        else response.reservedMemberNum(yata.getYataMembers().size());
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
        response.feulTank(yata.getMember().getFuelTank());
        if (yata.getYataMembers() != null) {
            response.yataMembers(yata.getYataMembers().stream()
                    .map(yataMember -> {
                        return YataMemberDto.Response.builder()
                                .yataId(yataMember.getYata().getYataId())
                                .yataMemberId(yataMember.getYataMemberId())
                                .yataPaid(yataMember.isYataPaid())
                                .goingStatus(yataMember.getGoingStatus()).build();
                    }).collect(Collectors.toList())); //여기선 넣어주고
        }
        return response.build();
    }

    //todo strPoint, destination mapping
    default List<YataDto.Response> yatasToYataResponses(List<Yata> yatas) {
        if (yatas == null) {
            return null;
        }
        return yatas.stream()
                .map(yata -> {

                    YataDto.Response.ResponseBuilder response = YataDto.Response.builder();
                    if (yata.getYataMembers() == null) response.reservedMemberNum(0);
                    else response.reservedMemberNum(yata.getYataMembers().size());

                    return response.yataId(yata.getYataId())
                            .postStatus(yata.getPostStatus())
                            .yataStatus(yata.getYataStatus())
                            .departureTime(yata.getDepartureTime())
                            .timeOfArrival(yata.getTimeOfArrival())
                            .title(yata.getTitle())
                            .specifics(yata.getSpecifics())
                            .createdAt(yata.getCreatedAt())
                            .modifiedAt(yata.getModifiedAt())
                            .maxWaitingTime(yata.getMaxWaitingTime())
                            .maxPeople(yata.getMaxPeople())
                            .amount(yata.getAmount())
                            .carModel(yata.getCarModel())
                            .email(yata.getMember().getEmail())
                            .strPoint(locationToResponse(yata.getStrPoint()))
                            .destination(locationToResponse(yata.getDestination()))
                            .nickName(yata.getMember().getNickname())
                            .feulTank(yata.getMember().getFuelTank())
                            .yataMembers(null)
                            .build();
                }).collect(Collectors.toList());
    }

    default Slice<YataDto.Response> yatasToYataSliceResponses(Slice<Yata> yatas) {
        if (yatas == null) {
            return null;
        }

        List<YataDto.Response> responses = yatasToYataResponses(yatas.getContent());

        return new SliceImpl<>(responses, yatas.getPageable(), yatas.hasNext());
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

        response.longitude(location.getLocation().getX());
        response.latitude(location.getLocation().getY());
        response.address(location.getAddress());

        return response.build();
    }
}


