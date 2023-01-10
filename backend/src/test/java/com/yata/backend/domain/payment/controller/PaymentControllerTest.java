package com.yata.backend.domain.payment.controller;

import com.google.gson.Gson;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.member.factory.MemberFactory;
import com.yata.backend.domain.payment.config.TossPaymentConfig;
import com.yata.backend.domain.payment.dto.PayType;
import com.yata.backend.domain.payment.dto.PaymentDto;
import com.yata.backend.domain.payment.dto.PaymentResDto;
import com.yata.backend.domain.payment.entity.Payment;
import com.yata.backend.domain.payment.factory.PaymentFactory;
import com.yata.backend.domain.payment.service.PaymentService;
import com.yata.backend.domain.payment.service.PaymentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.Stream;

import static com.yata.backend.common.request.ResultActionsUtils.postRequest;
import static com.yata.backend.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest extends AbstractControllerTest {
    @Autowired
    private Gson gson;
    private final String BASE_URL = "/api/v1/payments";

    @MockBean
    private PaymentServiceImpl paymentService;
    @MockBean
    private TossPaymentConfig tossPaymentConfig;

    @Test
    @DisplayName("토스 결제 요청 전 상품 등록 후 결제 요청할 DATA 받기")
    @WithMockUser
    void requestTossPayment() throws Exception {
        //given
        PaymentDto paymentDto = PaymentFactory.createPaymentDto();
        Payment paymentResDto = PaymentFactory.createPayment(
                MemberFactory.createMember(new BCryptPasswordEncoder()));
        given(paymentService.requestTossPayment(any(), any())).willReturn(paymentResDto);
        given(tossPaymentConfig.getFailUrl()).willReturn("http://localhost:8080/api/v1/payments/toss/fail");
        given(tossPaymentConfig.getSuccessUrl()).willReturn("http://localhost:8080/api/v1/payments/toss/success");
        //when
        String content = gson.toJson(paymentDto);
        ResultActions resultActions = postRequest(mockMvc, BASE_URL + "/toss", content);
        resultActions.andExpect(status().isOk());
        //then
        resultActions.andDo(document("payment-toss",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                        headerWithName("Authorization").description("Bearer Token")
                ),
                requestFields(
                        fieldWithPath("payType").type(JsonFieldType.STRING)
                                .description(
                                        Stream.of(PayType.values()).map(PayType::name)
                                                .reduce((a, b) -> a + ", " + b).get()),
                        fieldWithPath("amount").type(JsonFieldType.NUMBER).description("결제 요청 금액"),
                        fieldWithPath("orderName").type(JsonFieldType.STRING).description("주문 이름")
                ),
                responseFields(
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결제 요청 데이터"),
                        fieldWithPath("data.payType").type(JsonFieldType.STRING)
                                .description(
                                        Stream.of(PayType.values()).map(PayType::name)
                                                .reduce((a, b) -> a + ", " + b).get()),
                        fieldWithPath("data.orderId").type(JsonFieldType.STRING).description("주문 번호"),
                        fieldWithPath("data.amount").type(JsonFieldType.NUMBER).description("결제 요청 금액"),
                        fieldWithPath("data.orderName").type(JsonFieldType.STRING).description("주문 이름"),
                        fieldWithPath("data.customerEmail").type(JsonFieldType.STRING).description("고객 이메일"),
                        fieldWithPath("data.customerName").type(JsonFieldType.STRING).description("고객 이름"),
                        fieldWithPath("data.successUrl").type(JsonFieldType.STRING).description("주문 성공시 redirect 할 url"),
                        fieldWithPath("data.failUrl").type(JsonFieldType.STRING).description("주문 실패시 redirect 할 url"),
                        fieldWithPath("data.failReason").type(JsonFieldType.STRING).description("주문 실패 err 메세지 "),
                        fieldWithPath("data.cancelYN").type(JsonFieldType.BOOLEAN).description("주문 취소 여부"),
                        fieldWithPath("data.cancelReason").type(JsonFieldType.NULL).description("주문 취소 사유"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("최초 주문 신청 날짜")

                )
        ));

    }

    @Test
    void tossPaymentSuccess() {
    }

    @Test
    void tossPaymentFail() {
    }

    @Test
    void tossPaymentCancelPoint() {
    }
}