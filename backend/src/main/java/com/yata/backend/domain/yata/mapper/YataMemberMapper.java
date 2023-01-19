package com.yata.backend.domain.yata.mapper;

import com.yata.backend.domain.yata.dto.YataMemberDto;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.Yata;
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
                .map(yataMember -> {
                    return YataMemberDto.Response.builder()
                            .yataMemberId(yataMember.getYataMemberId())
                            .yataId(yataMember.getYata().getYataId())
                            .yataRequestId(yataMember.getYata().getYataRequests().get(0).getYataRequestId())
                            .approvalStatus(yataMember.getYata().getYataRequests().get(0).getApprovalStatus())
                            .build();
                }).collect(Collectors.toList());
    }
}
