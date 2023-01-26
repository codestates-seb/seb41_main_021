package com.yata.backend.domain.payHistory.mapper;

import com.yata.backend.domain.payHistory.dto.PayHistoryDto;
import com.yata.backend.domain.payHistory.entity.PayHistory;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PayHistoryMapper {

    default List<PayHistoryDto.Response> payHistoryToPayHistoryResponse(List<PayHistory> payHistories) {
        if (payHistories == null) {
            return null;
        }

        return payHistories.stream()
                .map(payHistory -> PayHistoryDto.Response.builder()
                        .payHistoryId(payHistory.getPayHistoryId())
                        .email(payHistory.getMember().getEmail())
                        .nickname(payHistory.getMember().getNickname())
                        .paidPrice(payHistory.getPaidPrice())
                        .point(payHistory.getMember().getPoint())
                        .createdAt(payHistory.getCreatedAt())
                        .type(payHistory.getType())
                        .build()).collect(Collectors.toList());
    }
}
