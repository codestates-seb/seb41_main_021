package com.yata.backend.domain.payment.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.payment.config.TossPaymentConfig;
import com.yata.backend.domain.payment.dto.PaymentSuccessDto;
import com.yata.backend.domain.payment.entity.Payment;
import com.yata.backend.domain.payment.repository.JpaPaymentRepository;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import net.minidev.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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
public class PaymentServiceImpl implements PaymentService {
    private final JpaPaymentRepository paymentRepository;
    private final MemberService memberService;
    private final TossPaymentConfig tossPaymentConfig;


    public PaymentServiceImpl(JpaPaymentRepository paymentRepository, MemberService memberService, TossPaymentConfig tossPaymentConfig) {
        this.paymentRepository = paymentRepository;
        this.memberService = memberService;
        this.tossPaymentConfig = tossPaymentConfig;
    }

    public Payment requestPayment(Payment payment, String userEmail) {
        Member member = memberService.findMember(userEmail);
        if (payment.getAmount() < 1000) {
            throw new CustomLogicException(ExceptionCode.INVALID_PAYMENT_AMOUNT);
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
        memberService.updateMemberCache(payment.getCustomer());
        return result;
    }

    @Transactional
    public PaymentSuccessDto requestPaymentAccept(String paymentKey, String orderId, Long amount) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders();
        JSONObject params = new JSONObject();
        params.put("orderId", orderId);
        params.put("amount", amount);

        PaymentSuccessDto result = null;
        try {
            result = restTemplate.postForObject(TossPaymentConfig.URL + paymentKey,
                    new HttpEntity<>(params, headers),
                    PaymentSuccessDto.class);
        } catch (Exception e) {
            throw new CustomLogicException(ExceptionCode.ALREADY_APPROVED);
        }

        return result;

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

    @Transactional
    public Map cancelPaymentPoint(String userEmail, String paymentKey, String cancelReason) {
        Payment payment = paymentRepository.findByPaymentKeyAndCustomer_Email(paymentKey, userEmail).orElseThrow(() -> {
            throw new CustomLogicException(ExceptionCode.PAYMENT_NOT_FOUND);
        });
        // 취소 할려는데 포인트가 그만큼 없으면 환불 몬하지~
        if (payment.getCustomer().getPoint() >= payment.getAmount()) {
            payment.setCancelYN(true);
            payment.setCancelReason(cancelReason);
            payment.getCustomer().setPoint(payment.getCustomer().getPoint() - payment.getAmount());
            return tossPaymentCancel(paymentKey, cancelReason);
        }

        throw new CustomLogicException(ExceptionCode.PAYMENT_NOT_ENOUGH_POINT);
    }

    public Map tossPaymentCancel(String paymentKey, String cancelReason) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders();
        JSONObject params = new JSONObject();
        params.put("cancelReason", cancelReason);

        return restTemplate.postForObject(TossPaymentConfig.URL + paymentKey + "/cancel",
                new HttpEntity<>(params, headers),
                Map.class);
    }

    @Override
    public Slice<Payment> findAllChargingHistories(String username, Pageable pageable) {
        memberService.verifyMember(username);
        return paymentRepository.findAllByCustomer_Email(username,
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.Direction.DESC, "paymentId")
        );
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String encodedAuthKey = new String(
                Base64.getEncoder().encode((tossPaymentConfig.getTestSecretKey() + ":").getBytes(StandardCharsets.UTF_8)));
        headers.setBasicAuth(encodedAuthKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
