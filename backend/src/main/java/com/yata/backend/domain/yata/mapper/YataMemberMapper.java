package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.YataMemberDto;
import com.yata.backend.domain.yata.entity.YataMember;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Slice;

@Mapper(componentModel = "spring")
public interface YataMemberMapper {
    YataMember yataMemberPostDtoToYataMember(YataMemberDto.Post requestBody);

    YataMemberDto.Response yataMemberToYataMemberResponse(YataMember yataMember);
//    Slice<YataMemberDto.Response> yataMembersToYataMembersResponses(Slice<YataMember> yataMembers);
}
