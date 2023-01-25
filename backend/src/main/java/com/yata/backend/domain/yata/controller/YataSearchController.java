package com.yata.backend.domain.yata.controller;

import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.mapper.YataMapper;
import com.yata.backend.domain.yata.service.YataSearchService;
import com.yata.backend.global.response.SliceInfo;
import com.yata.backend.global.response.SliceResponseDto;
import org.locationtech.jts.io.ParseException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/yata/search")
public class YataSearchController {

    private YataSearchService yataSearchService;
    private YataMapper mapper;

    public YataSearchController(YataSearchService yataSearchService, YataMapper mapper) {
        this.yataSearchService = yataSearchService;
        this.mapper = mapper;
    }


    @GetMapping("/location")
    public ResponseEntity getLocation(@RequestParam double startLon,
                                      @RequestParam double startLat,
                                      @RequestParam String startAddress,
                                      @RequestParam double endLon,
                                      @RequestParam double endLat,
                                      @RequestParam String endAddress,
                                      @RequestParam double distance,
                                      @PageableDefault Pageable pageable) throws ParseException {
        LocationDto.Post startLocationDto = LocationDto.Post.of(startLon, startLat, startAddress);
        LocationDto.Post endLocationDto = LocationDto.Post.of(endLon, endLat, endAddress);
        Slice<Yata> yataList = yataSearchService.findYataByLocation(
                mapper.postToLocation(startLocationDto),
                mapper.postToLocation(endLocationDto),
                distance,
                pageable
        );
        //mapper.yatasToYataResponses(yataList)
        return ResponseEntity.ok(new SliceResponseDto<>(
                mapper.yatasToYataResponses(yataList.getContent()),
                new SliceInfo(pageable, yataList.getNumberOfElements(), yataList.hasNext())
        ));
    }
}
