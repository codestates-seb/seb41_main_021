package com.yata.backend.domain.payment.controller;

import com.yata.backend.domain.payment.config.TossPaymentConfig;
import com.yata.backend.domain.payment.dto.PaymentDto;
import com.yata.backend.domain.payment.dto.PaymentFailDto;
import com.yata.backend.domain.payment.dto.PaymentResDto;
import com.yata.backend.domain.payment.service.PaymentService;
import com.yata.backend.global.response.SingleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final TossPaymentConfig tossPaymentConfig;

    public PaymentController(PaymentService paymentService, TossPaymentConfig tossPaymentConfig) {
        this.paymentService = paymentService;
        this.tossPaymentConfig = tossPaymentConfig;
    }

    @PostMapping("/toss")
    public ResponseEntity requestTossPayment(@AuthenticationPrincipal User principal, @RequestBody @Valid PaymentDto paymentReqDto) {
        PaymentResDto paymentResDto = paymentService.requestTossPayment(paymentReqDto.toEntity(), principal.getUsername()).toPaymentResDto();
        paymentResDto.setSuccessUrl(tossPaymentConfig.getSuccessUrl());
        paymentResDto.setFailUrl(tossPaymentConfig.getFailUrl());
        return ResponseEntity.ok().body(new SingleResponse<>(paymentResDto));
    }

    @GetMapping("/toss/success")
    public ResponseEntity tossPaymentSuccess(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Long amount
    ) {

        return ResponseEntity.ok().body(paymentService.tossPaymentSuccess(paymentKey, orderId, amount));
    }

    @GetMapping("/toss/fail")
    public ResponseEntity tossPaymentFail(
            @RequestParam String code,
            @RequestParam String message,
            @RequestParam String orderId
    ) {
        paymentService.tossPaymentFail(code, message, orderId);
        return ResponseEntity.ok().body(PaymentFailDto.builder()
                .errorCode(code)
                .errorMessage(message)
                .orderId(orderId)
                .build());
    }
}
