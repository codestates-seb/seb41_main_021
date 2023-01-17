package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.YataMemberDto;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface YataMemberMapper {
    YataMember yataMemberPostDtoToYataMember(YataMemberDto.Post requestBody);

    YataMemberDto.Response yataMemberToYataMemberResponse(YataMember yataMember);
    List<YataMemberDto.Response> yataMembersToYataMembersResponses(List<YataMember> yataMembers);
}
