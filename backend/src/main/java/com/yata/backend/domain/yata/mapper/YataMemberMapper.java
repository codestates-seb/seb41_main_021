package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.YataMemberDto;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;
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
                        .goingStatus(yataMember.getGoingStatus())
                        .build()).collect(Collectors.toList());
    }

//    default YataMemberDto.pointPaymentResponse yataMemberToYataMemberResponse(YataMember yataMember) {
//        if (yataMember == null) {
//            return null;
//        }
//
//        long yataMemberId = yataMember.getYataMemberId();
//        long yataId = yataMember.getYata().getYataId();
//        boolean yataPaid = yataMember.isYataPaid();
//        long point = yataMember.getMember().getPoint();
//        YataMember.GoingStatus goingStatus = yataMember.getGoingStatus();
//
//        YataMemberDto.pointPaymentResponse response = new YataMemberDto.pointPaymentResponse(
//                yataMemberId, yataId, yataPaid, point, goingStatus);
//
//        return response;
//    }
}
