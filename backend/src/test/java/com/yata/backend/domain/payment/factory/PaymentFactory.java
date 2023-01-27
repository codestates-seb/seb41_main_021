package com.yata.backend.domain.payment.factory;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.factory.MemberFactory;
import com.yata.backend.domain.payment.dto.*;
import com.yata.backend.domain.payment.entity.Payment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaymentFactory {
    public static PaymentDto createPaymentDto() {
        return PaymentDto.builder()
                .payType(PayType.CARD)
                .amount(15000L)
                .orderName("포인트 충전")
                .yourSuccessUrl("http://localhost:3000/payment/success")
                .yourFailUrl("http://localhost:3000/payment/fail")
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

    public static PaymentSuccessDto createPaymentSuccessDto() {
        PaymentSuccessDto paymentSuccessDto = new PaymentSuccessDto();
        paymentSuccessDto.setPaymentKey("paymentKey");
        paymentSuccessDto.setApprovedAt("2021-08-01 00:00:00");
        paymentSuccessDto.setMethod(PayType.CARD.getDescription());
        paymentSuccessDto.setMid("mid");
        paymentSuccessDto.setOrderId(UUID.randomUUID().toString());
        paymentSuccessDto.setOrderName("orderName");
        paymentSuccessDto.setRequestedAt("2021-08-01 00:00:00");
        paymentSuccessDto.setStatus("status");
        paymentSuccessDto.setTotalAmount("15000");
        paymentSuccessDto.setVersion("1.3");
        paymentSuccessDto.setVat("1364");
        paymentSuccessDto.setBalanceAmount("15000");
        paymentSuccessDto.setSuppliedAmount("13636");
        paymentSuccessDto.setCurrency("KRW");
        paymentSuccessDto.setUseEscrow("false");
        paymentSuccessDto.setCultureExpense("false");
        paymentSuccessDto.setType("NORMAL");
        PaymentSuccessCardDto paymentSuccessCardDto = new PaymentSuccessCardDto();
        paymentSuccessCardDto.setAcquireStatus("acquireStatus");
        paymentSuccessCardDto.setApproveNo("approveNo");
        paymentSuccessCardDto.setCardType("cardType");
        paymentSuccessCardDto.setCompany("company");
        paymentSuccessCardDto.setInstallmentPlanMonths("0");
        paymentSuccessCardDto.setIsInterestFree("false");
        paymentSuccessCardDto.setNumber("number");
        paymentSuccessCardDto.setOwnerType("ownerType");
        paymentSuccessCardDto.setReceiptUrl("receiptUrl");
        paymentSuccessCardDto.setUseCardPoint("false");
        paymentSuccessDto.setCard(paymentSuccessCardDto);

        return paymentSuccessDto;

    }

    public static List<Payment> createPaymentList() throws java.text.ParseException, org.locationtech.jts.io.ParseException {
        Member member = MemberFactory.createMember(new BCryptPasswordEncoder());

        List<Payment> paymentList = new ArrayList<>();
        for(int i=0; i<10; i++){
            paymentList.add(createPayment(member));
        }
        return paymentList;
    }

    public static ChargingHistoryDto createYataMemberResponseDto(Payment payment) {

        return ChargingHistoryDto.builder()
                .paymentHistoryId(payment.getPaymentId())
                .amount(payment.getAmount())
                .orderName(payment.getOrderName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static List <ChargingHistoryDto> createChargingHistoryDtoList(List<Payment> yataMembersList){
        List <ChargingHistoryDto> chargingHistoryDtoList = new ArrayList<>();
        for(Payment yataMember : yataMembersList){
            chargingHistoryDtoList.add(createYataMemberResponseDto(yataMember));
        }
        return chargingHistoryDtoList;
    }
}