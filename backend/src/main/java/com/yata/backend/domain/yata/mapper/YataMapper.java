package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import org.mapstruct.Mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

//date to String
//Date from = new Date();
//
//SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd HH:mm");
//
//String to = transFormat.format(from);

@Mapper(componentModel = "spring")
public interface YataMapper {
//    Yata yataPostDtoToYata(YataDto.YataPost requestBody);

    default Yata yataPostDtoToYata(YataDto.YataPost requestBody){
        if ( requestBody == null ) {
            return null;
        }

        Yata.YataBuilder yata = Yata.builder();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm E");
        try {
            if ( requestBody.getDepartureTime() != null ) {
                yata.departureTime(transFormat.parse( requestBody.getDepartureTime() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( requestBody.getTimeOfArrival() != null ) {
                yata.timeOfArrival( transFormat.parse( requestBody.getTimeOfArrival()) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        //transFormat.parse(post.getDepartureTime())
        yata.title( requestBody.getTitle() );
        yata.content( requestBody.getContent() );
        yata.maxWaitingTime( requestBody.getMaxWaitingTime() );
        yata.carModel( requestBody.getCarModel() );
        yata.maxPeople( requestBody.getMaxPeople() );
        yata.amount( requestBody.getAmount() );
        yata.strPoint( postToLocation( requestBody.getStrPoint() ) );
        yata.destination( postToLocation( requestBody.getDestination() ) );

        return yata.build();
    }
    Yata nataPostDtoToYata(YataDto.YataPost requestBody);
    Yata yataPatchToYata(YataDto.Patch requestBody);
    YataDto.Response yataToYataResponse(Yata yata);

    List<YataDto.Response> yatasToYataResponses(List<Yata> yatas);
    default Location postToLocation(LocationDto.Post post) {
        if ( post == null ) {
            return null;
        }

        Location.LocationBuilder location = Location.builder();

        location.longitude( post.getLongitude() );
        location.latitude( post.getLatitude() );
        location.address( post.getAddress() );

        return location.build();
    }

    default LocationDto.Response locationToResponse(Location location) {
        if ( location == null ) {
            return null;
        }

        LocationDto.Response.ResponseBuilder response = LocationDto.Response.builder();

        response.longitude( location.getLongitude() );
        response.latitude( location.getLatitude() );
        response.address( location.getAddress() );

        return response.build();
    }
}


