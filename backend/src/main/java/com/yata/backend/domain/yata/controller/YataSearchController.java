package com.yata.backend.domain.yata.controller;

import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataStatus;
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
    // TODO 어떻게 코드 이쁘게 짜지?

    @GetMapping("/location")
    public ResponseEntity getLocation(@RequestParam(required = false) Double startLon,
                                      @RequestParam(required = false) Double startLat,
                                      @RequestParam(required = false) String startAddress,
                                      @RequestParam(required = false) Double endLon,
                                      @RequestParam(required = false) Double endLat,
                                      @RequestParam(required = false) String endAddress,
                                      @RequestParam double distance,
                                      @RequestParam(required = false) String yataStatus,
                                      @PageableDefault Pageable pageable) throws ParseException {
        LocationDto.Post startLocationDto = LocationDto.Post.of(
                startLon == null ? 0 : startLon,
                startLat == null ? 0 : startLat,
                startAddress == null ? "" : startAddress
        );
        LocationDto.Post endLocationDto = LocationDto.Post.of(
                endLon == null ? 0 : endLon,
                endLat == null ? 0 : endLat,
                endAddress == null ? "" : endAddress
        );
        Slice<Yata> yataList = yataSearchService.findYataByLocation(
                mapper.postToLocation(startLocationDto),
                mapper.postToLocation(endLocationDto),
                distance,
                yataStatus == null ? null : YataStatus.valueOf(yataStatus),
                pageable
        );
        //mapper.yatasToYataResponses(yataList)
        return ResponseEntity.ok(new SliceResponseDto<>(
                mapper.yatasToYataResponses(yataList.getContent()),
                new SliceInfo(pageable, yataList.getNumberOfElements(), yataList.hasNext())
        ));
    }
}
