package com.yata.backend.domain.payment.entity;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.payment.dto.PayType;
import com.yata.backend.domain.payment.dto.PaymentResDto;
import com.yata.backend.global.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Payment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false, unique = true)
    private Long paymentId;
    @Column(nullable = false , name = "pay_type")
    @Enumerated(EnumType.STRING)
    private PayType payType;
    @Column(nullable = false , name = "pay_amount")
    private Long amount;
    @Column(nullable = false , name = "pay_name")
    private String orderName;
    @Column(nullable = false , name = "order_id")
    private String orderId;

    private boolean paySuccessYN;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Member customer;
    @Column
    private String paymentKey;
    @Column
    private String failReason;

    @Column
    private boolean cancelYN;
    @Column
    private String cancelReason;
    public PaymentResDto toPaymentResDto() {
        return PaymentResDto.builder()
                .payType(payType.getDescription())
                .amount(amount)
                .orderName(orderName)
                .orderId(orderId)
                .customerEmail(customer.getEmail())
                .customerName(customer.getName())
                .createdAt(getCreatedAt().toString())
                .cancelYN(cancelYN)
                .failReason(failReason)
                .build();
    }



}
