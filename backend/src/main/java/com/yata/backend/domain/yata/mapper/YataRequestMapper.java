package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface YataRequestMapper {
    default YataRequest yataRequestPostDtoToYataRequest(long yataId, YataRequestDto.Post requestBody) {
        return null;
    }
    YataRequestDto.Response yataRequestToYataRequestResponse(YataRequest yataRequest);
    List<YataRequestDto.Response> yataRequestsToYataRequestResponses(List<YataRequest> yataRequests);
}
