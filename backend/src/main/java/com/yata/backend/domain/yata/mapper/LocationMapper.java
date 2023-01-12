package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.entity.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location locationPostToLocation(LocationDto.Post locationPostDto);

    Location locationPatchToLocation(LocationDto.Patch locationPatchDto);


    Location locationToLocationResponseDto(Location location);
}
