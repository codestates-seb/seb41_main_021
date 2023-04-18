package com.yata.backend.domain.payment.controller;

import com.yata.backend.domain.payment.config.TossPaymentConfig;
import com.yata.backend.domain.payment.dto.PaymentDto;
import com.yata.backend.domain.payment.dto.PaymentFailDto;
import com.yata.backend.domain.payment.dto.PaymentResDto;
import com.yata.backend.domain.payment.entity.Payment;
import com.yata.backend.domain.payment.mapper.PaymentMapper;
import com.yata.backend.domain.payment.service.PaymentServiceImpl;
import com.yata.backend.global.response.SingleResponse;
import com.yata.backend.global.response.SliceInfo;
import com.yata.backend.global.response.SliceResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
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
    private final PaymentServiceImpl paymentService;
    private final TossPaymentConfig tossPaymentConfig;
    private final PaymentMapper mapper;

    public PaymentController(PaymentServiceImpl paymentService, TossPaymentConfig tossPaymentConfig, PaymentMapper mapper) {
        this.paymentService = paymentService;
        this.tossPaymentConfig = tossPaymentConfig;
        this.mapper = mapper;
    }

    @PostMapping("/toss")
    public ResponseEntity requestTossPayment(@AuthenticationPrincipal User principal, @RequestBody @Valid PaymentDto paymentReqDto) {
        PaymentResDto paymentResDto = paymentService.requestPayment(paymentReqDto.toEntity(), principal.getUsername()).toPaymentResDto();
        paymentResDto.setSuccessUrl(paymentReqDto.getYourSuccessUrl() == null ? tossPaymentConfig.getSuccessUrl() : paymentReqDto.getYourSuccessUrl());
        paymentResDto.setFailUrl(paymentReqDto.getYourFailUrl() == null ? tossPaymentConfig.getFailUrl() : paymentReqDto.getYourFailUrl());
        return ResponseEntity.ok().body(new SingleResponse<>(paymentResDto));
    }
    @GetMapping("/toss/success")
    public ResponseEntity tossPaymentSuccess(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Long amount
    ) {

        return ResponseEntity.ok().body(new SingleResponse<>(paymentService.tossPaymentSuccess(paymentKey, orderId, amount)));
    }

    @GetMapping("/toss/fail")
    public ResponseEntity tossPaymentFail(
            @RequestParam String code,
            @RequestParam String message,
            @RequestParam String orderId
    ) {
        paymentService.tossPaymentFail(code, message, orderId);
        return ResponseEntity.ok().body(new SingleResponse<>(
                PaymentFailDto.builder()
                        .errorCode(code)
                        .errorMessage(message)
                        .orderId(orderId)
                        .build()
        ));
    }

    @PostMapping("/toss/cancel/point")
    public ResponseEntity tossPaymentCancelPoint(
            @AuthenticationPrincipal User principal,
            @RequestParam String paymentKey,
            @RequestParam String cancelReason
    ) {
        return ResponseEntity.ok().body(new SingleResponse<>(
                paymentService
                        .cancelPaymentPoint(principal.getUsername(), paymentKey, cancelReason)));
    }

    @GetMapping("/history")
    public ResponseEntity getChargingHistory(@AuthenticationPrincipal User authMember,
                                             Pageable pageable) {
        Slice<Payment> chargingHistories = paymentService.findAllChargingHistories(authMember.getUsername(), pageable);
        SliceInfo sliceInfo = new SliceInfo(pageable, chargingHistories.getNumberOfElements(), chargingHistories.hasNext());
        return new ResponseEntity<>(
                new SliceResponseDto<>(mapper.chargingHistoryToChargingHistoryResponses(chargingHistories.getContent()), sliceInfo), HttpStatus.OK);
    }
}
