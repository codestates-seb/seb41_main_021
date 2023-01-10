package com.yata.backend.domain.payment.factory;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.payment.dto.PayType;
import com.yata.backend.domain.payment.dto.PaymentDto;
import com.yata.backend.domain.payment.dto.PaymentResDto;
import com.yata.backend.domain.payment.entity.Payment;

import java.util.UUID;

public class PaymentFactory {
   public static PaymentDto createPaymentDto() {
       return PaymentDto.builder()
               .payType(PayType.CARD)
               .amount(15000L)
               .orderName("포인트 충전")
               .build();
   }

    public static Payment createPayment(Member member) {
        return Payment.builder()
                .payType(PayType.CARD)
                .amount(15000L)
                .orderName("포인트 충전")
                .orderId(UUID.randomUUID().toString())
                .paySuccessYN(false)
                .paymentKey(UUID.randomUUID().toString())
                .paymentId(1L)
                .cancelReason("취소 사유")
                .cancelYN(true)
                .failReason("실패 사유")
                .customer(member)
                .build();
    }
}