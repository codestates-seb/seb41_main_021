package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.YataMemberDto;
import com.yata.backend.domain.yata.entity.YataMember;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface YataMemberMapper {
    default List<YataMemberDto.Response> yataMembersToYataMembersResponses(List<YataMember> yataMembers) {
        if (yataMembers == null) {
            return null;
        }

        return yataMembers.stream()
                .map(yataMember -> YataMemberDto.Response.builder()
                        .yataMemberId(yataMember.getYataMemberId())
                        .yataId(yataMember.getYata().getYataId())
                        .email(yataMember.getMember().getEmail())
                        .nickname(yataMember.getMember().getNickname())
                        .yataPaid(yataMember.isYataPaid())
                        .imgUrl(yataMember.getMember().getImgUrl() != null ? yataMember.getMember().getImgUrl().getUrl() : null)
                        .boardingPersonCount(yataMember.getBoardingPersonCount())
                        .goingStatus(yataMember.getGoingStatus())
                        .build()).collect(Collectors.toList());
    }
}
