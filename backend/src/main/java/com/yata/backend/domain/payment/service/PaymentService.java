package com.yata.backend.domain.payment.service;

import com.yata.backend.domain.payment.dto.PaymentSuccessDto;
import com.yata.backend.domain.payment.entity.Payment;

public interface PaymentService {
    Payment requestTossPayment(Payment payment, String userEmail);
    PaymentSuccessDto tossPaymentSuccess(String paymentKey, String orderId, Long amount);
    PaymentSuccessDto requestPaymentAccept(String paymentKey, String orderId, Long amount);
    Payment verifyPayment(String orderId, Long amount);
}
