package com.yata.backend.domain.payment.dto;

import com.yata.backend.domain.payment.entity.Payment;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private PayType payType;
    private Long amount;
    private String orderName;


    public Payment toEntity() {
        return Payment.builder()
                .payType(payType)
                .amount(amount)
                .orderName(orderName)
                .orderId(UUID.randomUUID().toString())
                .paySuccessYN(false)
                .build();
    }
}
