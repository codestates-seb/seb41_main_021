package com.yata.backend.domain.payment.dto;

import lombok.Data;

@Data
public class PaymentSuccessDto {
   String mid;
   String version;
   String paymentKey;
    String orderId;
   String orderName;
   String currency;
   String method;
    String totalAmount;
    String balanceAmount;
    String suppliedAmount;
    String vat;
    String status;
    String requestedAt;
    String approvedAt;
    String useEscrow;
    String cultureExpense;
    PaymentSuccessCardDto card;
    String type;
}
//{mId=tvivarepublica, lastTransactionKey=0F60D0A2CBEB4467F1FD562FC401B986, paymentKey=ly05n91dEvLex6BJGQOVDZKBQd0DR8W4w2zNbgaYRMPoqmDX, orderId=ac52c6bb-9d1f-44de-858f-3a2cdb113726, orderName=포인트 충전, taxExemptionAmount=0, status=DONE, requestedAt=2023-01-10T14:59:58+09:00, approvedAt=2023-01-10T15:00:38+09:00, useEscrow=false, cultureExpense=false, card={company=, issuerCode=, acquirerCode=null, number=, installmentPlanMonths=0, isInterestFree=false, approveNo=10030038, useCardPoint=false, cardType=미확인, ownerType=미확인, acquireStatus=READY, receiptUrl=https://dashboard.tosspayments.com/sales-slip?transactionId=muvOdDyeJe24BKPIGByRKw%2BaoD5IQgtyF7MDqexQel8U9gz5EDlVIFg3wpRK0UhA&ref=PX, amount=0}, virtualAccount=null, transfer=null, mobilePhone=null, giftCertificate=null, cashReceipt=null, discount=null, cancels=null, secret=ps_BE92LAa5PVbRPogbp6BV7YmpXyJj, type=NORMAL, easyPay=토스결제, easyPayAmount=15000, easyPayDiscountAmount=0, isPartialCancelable=true, receipt={url=https://dashboard.tosspayments.com/sales-slip?transactionId=PsG7ffB2lzPtJMsForfdIlCSaEElJyxEscDuNVP9bq9NeunkFOdSOJWKIKGug0mN&ref=PX}, checkout={url=https://api.tosspayments.com/v1/payments/ly05n91dEvLex6BJGQOVDZKBQd0DR8W4w2zNbgaYRMPoqmDX/checkout}, transactionKey=0F60D0A2CBEB4467F1FD562FC401B986, currency=KRW, totalAmount=15000, balanceAmount=15000, suppliedAmount=13636, vat=1364, taxFreeAmount=0, method=카드, version=1.3}