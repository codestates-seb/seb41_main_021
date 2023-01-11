package com.yata.backend.domain.payment.controller;

import com.google.gson.Gson;
import com.yata.backend.common.request.ResultActionsUtils;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.member.factory.MemberFactory;
import com.yata.backend.domain.payment.config.TossPaymentConfig;
import com.yata.backend.domain.payment.dto.PayType;
import com.yata.backend.domain.payment.dto.PaymentDto;
import com.yata.backend.domain.payment.dto.PaymentResDto;
import com.yata.backend.domain.payment.dto.PaymentSuccessDto;
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

import java.util.Map;
import java.util.stream.Stream;

import static com.yata.backend.common.request.ResultActionsUtils.postRequest;
import static com.yata.backend.domain.payment.factory.PaymentFactory.createPaymentSuccessDto;
import static com.yata.backend.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
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
    @DisplayName("토스 결제 성공시")
    @WithMockUser
    void tossPaymentSuccess() throws Exception {
        // given
        PaymentSuccessDto paymentSuccessDto = createPaymentSuccessDto();
        given(paymentService.tossPaymentSuccess(anyString(), anyString(), anyLong())).willReturn(paymentSuccessDto);
        String requestParam = "?paymentKey=" + paymentSuccessDto.getPaymentKey() + "&orderId=" + paymentSuccessDto.getOrderId() + "&amount=" + paymentSuccessDto.getTotalAmount();
        // when
        ResultActions resultActions = ResultActionsUtils.getRequest(mockMvc, BASE_URL + "/toss/success" + requestParam);
        resultActions.andExpect(status().isOk());
        // then

        resultActions.andDo(document("payment-toss-success",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                        headerWithName("Authorization").description("Bearer Token")
                ),
                requestParameters(
                        parameterWithName("paymentKey").description("결제 키"),
                        parameterWithName("amount").description("결제 요청 금액"),
                        parameterWithName("orderId").description("주문 ID"),
                        parameterWithName("_csrf").description("결제 총 금액")
                ),
                responseFields(
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결제 요청 데이터"),
                        fieldWithPath("data.mid").type(JsonFieldType.STRING).description("가맹점 아이디"),
                        fieldWithPath("data.version").type(JsonFieldType.STRING).description("결제 버전"),
                        fieldWithPath("data.paymentKey").type(JsonFieldType.STRING).description("결제 키"),
                        fieldWithPath("data.orderId").type(JsonFieldType.STRING).description("주문 번호"),
                        fieldWithPath("data.orderName").type(JsonFieldType.STRING).description("주문 이름"),
                        fieldWithPath("data.currency").type(JsonFieldType.STRING).description("화폐"),
                        fieldWithPath("data.method").type(JsonFieldType.STRING).description("결제 수단"),
                        fieldWithPath("data.totalAmount").type(JsonFieldType.STRING).description("결제 총 금액"),
                        fieldWithPath("data.balanceAmount").type(JsonFieldType.STRING).description("취소 할 수 있는 결제 잔액"),
                        fieldWithPath("data.suppliedAmount").type(JsonFieldType.STRING).description("세금 제외 결제 금액"),
                        fieldWithPath("data.vat").type(JsonFieldType.STRING).description("세금"),
                        fieldWithPath("data.status").type(JsonFieldType.STRING).description("결제 상태"),
                        fieldWithPath("data.requestedAt").type(JsonFieldType.STRING).description("결제 요청 시간"),
                        fieldWithPath("data.approvedAt").type(JsonFieldType.STRING).description("결제 승인 시간"),
                        fieldWithPath("data.useEscrow").type(JsonFieldType.STRING).description("에스크로 사용 여부"),
                        fieldWithPath("data.cultureExpense").type(JsonFieldType.STRING).description("문화비 여부"),
                        fieldWithPath("data.card").type(JsonFieldType.OBJECT).description("결제 카드 정보"),
                        fieldWithPath("data.card.company").type(JsonFieldType.STRING).description("카드 회사"),
                        fieldWithPath("data.card.number").type(JsonFieldType.STRING).description("카드 번호"),
                        fieldWithPath("data.card.installmentPlanMonths").type(JsonFieldType.STRING).description("할부 개월 수"),
                        fieldWithPath("data.card.isInterestFree").type(JsonFieldType.STRING).description("무이자 여부"),
                        fieldWithPath("data.card.approveNo").type(JsonFieldType.STRING).description("승인 번호"),
                        fieldWithPath("data.card.useCardPoint").type(JsonFieldType.STRING).description("카드 포인트 사용 여부"),
                        fieldWithPath("data.card.cardType").type(JsonFieldType.STRING).description("카드 타입"),
                        fieldWithPath("data.card.ownerType").type(JsonFieldType.STRING).description("카드 소유자 타입"),
                        fieldWithPath("data.card.acquireStatus").type(JsonFieldType.STRING).description("카드 승인 상태"),
                        fieldWithPath("data.card.receiptUrl").type(JsonFieldType.STRING).description("카드 영수증 URL"),
                        fieldWithPath("data.type").type(JsonFieldType.STRING).description("결제 타입")


                )
        ));
    }

    @Test
    void tossPaymentFail() {
    }

    @Test
    @DisplayName("토스 결제 취소")
    @WithMockUser
    void tossPaymentCancelPoint() throws Exception {
        // given
        String requestParams = "?paymentKey=" + createPaymentSuccessDto().getPaymentKey() + "&cancelReason=" + "취소 사유";
        given(paymentService.cancelPaymentPoint(anyString(),anyString(),anyString())).willReturn(Map.of());
        // when
        ResultActions resultActions = ResultActionsUtils.postRequest(mockMvc, BASE_URL + "/toss/cancel/point" + requestParams , "");
        resultActions.andExpect(status().isOk());
        // then
        resultActions.andDo(document("payment-toss-cancel-point",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                        headerWithName("Authorization").description("Bearer Token")
                ),
                requestParameters(
                        parameterWithName("paymentKey").description("결제 키"),
                        parameterWithName("cancelReason").description("취소 사유"),
                        parameterWithName("_csrf").description("무시")
                )
        ));



    }
}