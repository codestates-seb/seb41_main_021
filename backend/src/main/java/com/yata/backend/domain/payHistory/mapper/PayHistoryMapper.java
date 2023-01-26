package com.yata.backend.domain.payHistory.mapper;

import com.yata.backend.domain.payHistory.dto.PayHistoryDto;
import com.yata.backend.domain.payHistory.entity.PayHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class PayHistoryMapper {

    PayHistoryDto.Response payHistoryToPayHistoryResponse(PayHistory payHistory) {
        if (payHistory == null) {
            return null;
        }

        long payHistoryId = payHistory.getPayHistoryId();
        String email = payHistory.getMember().getEmail();
        String nickname = payHistory.getMember().getNickname();
//        Long payPrice = payHistory.

        PayHistoryDto.Response response = new PayHistoryDto.Response(payHistoryId, email, nickname);

        return response;
    }
}
