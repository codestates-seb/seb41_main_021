package com.yata.backend.domain.payment.mapper;

import com.yata.backend.domain.payment.dto.ChargingHistoryDto;
import com.yata.backend.domain.payment.entity.Payment;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    default List<ChargingHistoryDto> chargingHistoryToChargingHistoryResponses(List<Payment> chargingHistories) {
        if (chargingHistories == null) {
            return null;
        }

        return chargingHistories.stream()
                .map(chargingHistory -> {
                    return ChargingHistoryDto.builder()
                            .paymentHistoryId(chargingHistory.getPaymentId())
                            .amount(chargingHistory.getAmount())
                            .orderName(chargingHistory.getOrderName())
                            .createdAt(chargingHistory.getCreatedAt())
                            .build();
                }).collect(Collectors.toList());
    }
}
