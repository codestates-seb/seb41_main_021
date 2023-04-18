package com.yata.backend.domain.payment.service;

import com.yata.backend.domain.payment.dto.PaymentSuccessDto;
import com.yata.backend.domain.payment.entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PaymentService {
    Payment requestPayment(Payment payment, String userEmail);
    PaymentSuccessDto tossPaymentSuccess(String paymentKey, String orderId, Long amount);
    PaymentSuccessDto requestPaymentAccept(String paymentKey, String orderId, Long amount);
    Slice<Payment> findAllChargingHistories(String username, Pageable pageable);
    Payment verifyPayment(String orderId, Long amount);
}
