package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.mapstruct.Mapper;

import java.util.List;

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
        yata.content(requestBody.getContent());
        yata.maxWaitingTime(requestBody.getMaxWaitingTime());
        yata.carModel(requestBody.getCarModel());
        yata.maxPeople(requestBody.getMaxPeople());
        yata.amount(requestBody.getAmount());
        yata.strPoint(postToLocation(requestBody.getStrPoint()));
        yata.destination(postToLocation(requestBody.getDestination()));

        return yata.build();
    }

    Yata yataPatchToYata(YataDto.Patch requestBody);

    default YataDto.Response yataToYataResponse(Yata yata){
        if ( yata == null ) {
            return null;
        }

        YataDto.Response.ResponseBuilder response = YataDto.Response.builder();

        if ( yata.getYataId() != null ) {
            response.yataId( yata.getYataId() );
        }
        response.departureTime( yata.getDepartureTime() );
        response.timeOfArrival( yata.getTimeOfArrival() );
        response.title( yata.getTitle() );
        response.content( yata.getContent() );
        response.maxWaitingTime( yata.getMaxWaitingTime() );
        response.maxPeople( yata.getMaxPeople() );
        response.amount( yata.getAmount() );
        response.carModel( yata.getCarModel() );
        response.strPoint( locationToResponse( yata.getStrPoint() ) );
        response.destination( locationToResponse( yata.getDestination() ) );
        response.postStatus( yata.getPostStatus() );
        response.yataStatus( yata.getYataStatus() );
        response.email(yata.getMember().getEmail());

        return response.build();
    }

    List<YataDto.Response> yatasToYataResponses(List<Yata> yatas);

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


