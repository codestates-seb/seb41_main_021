package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface YataMapper {
//    Yata yataPostDtoToYata(YataDto.YataPost requestBody);

    default Yata yataPostDtoToYata(YataDto.YataPost requestBody) {
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
        yata.postStatus(Yata.PostStatus.POST_WAITING);

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
        response.specifics( yata.getSpecifics() );
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

    default Location postToLocation(LocationDto.Post post) {
        if (post == null) {
            return null;
        }

        Location.LocationBuilder location = Location.builder();

        location.longitude(post.getLongitude());
        location.latitude(post.getLatitude());
        location.address(post.getAddress());


        return location.build();
    }

    default LocationDto.Response locationToResponse(Location location) {
        if (location == null) {
            return null;
        }

        LocationDto.Response.ResponseBuilder response = LocationDto.Response.builder();

        response.longitude(location.getLongitude());
        response.latitude(location.getLatitude());
        response.address(location.getAddress());

        return response.build();
    }
}


