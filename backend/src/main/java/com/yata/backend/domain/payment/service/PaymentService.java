package com.yata.backend.domain.payment.service;

import com.google.gson.JsonObject;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.payment.config.TossPaymentConfig;
import com.yata.backend.domain.payment.dto.PaymentSuccessDto;
import com.yata.backend.domain.payment.entity.Payment;
import com.yata.backend.domain.payment.repository.JpaPaymentRepository;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

@Service
@Transactional
public class PaymentService {
    private final JpaPaymentRepository paymentRepository;
    private final MemberService memberService;

    private final TossPaymentConfig tossPaymentConfig;


    public PaymentService(JpaPaymentRepository paymentRepository, MemberService memberService, TossPaymentConfig tossPaymentConfig) {
        this.paymentRepository = paymentRepository;
        this.memberService = memberService;
        this.tossPaymentConfig = tossPaymentConfig;
    }

    public Payment requestTossPayment(Payment payment, String userEmail) {
        Member member = memberService.findMember(userEmail);
        if (payment.getAmount() < 1000) {
            throw new IllegalArgumentException("최소 결제 금액은 1000원 입니다.");
        }
        payment.setCustomer(member);
        return paymentRepository.save(payment);
    }

    @Transactional
    public PaymentSuccessDto tossPaymentSuccess(String paymentKey, String orderId, Long amount) {
        Payment payment = verifyPayment(orderId, amount);
        PaymentSuccessDto result = requestPaymentAccept(paymentKey, orderId, amount);
        payment.setPaymentKey(paymentKey);
        payment.setPaySuccessYN(true);
        payment.getCustomer().setPoint(payment.getCustomer().getPoint() + amount);
        return result;
    }

    @Transactional
    public PaymentSuccessDto requestPaymentAccept(String paymentKey, String orderId, Long amount) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String encodedAuthKey = new String(
                Base64.getEncoder().encode((tossPaymentConfig.getTestSecretKey() + ":").getBytes(StandardCharsets.UTF_8)));
        headers.setBasicAuth(encodedAuthKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        JSONObject params = new JSONObject();
        params.put("orderId", orderId);
        params.put("amount", amount);

        return restTemplate.postForObject(TossPaymentConfig.ACCEPT_URL + paymentKey,
                new HttpEntity<>(params, headers),
                PaymentSuccessDto.class);

    }

    public Payment verifyPayment(String orderId, Long amount) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> {
            throw new CustomLogicException(ExceptionCode.PAYMENT_NOT_FOUND);
        });
        if (!payment.getAmount().equals(amount)) {
            throw new CustomLogicException(ExceptionCode.PAYMENT_AMOUNT_EXP);
        }
        return payment;
    }

    @Transactional
    public void tossPaymentFail(String code, String message, String orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> {
            throw new CustomLogicException(ExceptionCode.PAYMENT_NOT_FOUND);
        });
        payment.setPaySuccessYN(false);
        payment.setFailReason(message);
    }
}
