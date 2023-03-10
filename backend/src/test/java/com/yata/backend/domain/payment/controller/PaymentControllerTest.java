package com.yata.backend.domain.payment.controller;

import com.google.gson.Gson;
import com.yata.backend.common.request.ResultActionsUtils;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.member.factory.MemberFactory;
import com.yata.backend.domain.payment.config.TossPaymentConfig;
import com.yata.backend.domain.payment.dto.ChargingHistoryDto;
import com.yata.backend.domain.payment.dto.PayType;
import com.yata.backend.domain.payment.dto.PaymentDto;
import com.yata.backend.domain.payment.dto.PaymentSuccessDto;
import com.yata.backend.domain.payment.entity.Payment;
import com.yata.backend.domain.payment.factory.PaymentFactory;
import com.yata.backend.domain.payment.mapper.PaymentMapper;
import com.yata.backend.domain.payment.service.PaymentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.yata.backend.common.request.ResultActionsUtils.postRequest;
import static com.yata.backend.domain.payment.factory.PaymentFactory.createPaymentSuccessDto;
import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.utils.ApiDocumentUtils.getResponsePreProcessor;
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

    @MockBean
    private PaymentMapper mapper;

    @Test
    @DisplayName("?????? ?????? ?????? ??? ?????? ?????? ??? ?????? ????????? DATA ??????")
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
                        fieldWithPath("amount").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                        fieldWithPath("orderName").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("yourSuccessUrl").type(JsonFieldType.STRING).description("?????? ????????? ????????? URL,???????????? ?????? ?????? URL??? ??????"),
                        fieldWithPath("yourFailUrl").type(JsonFieldType.STRING).description("?????? ????????? ????????? URL , ???????????? ?????? ?????? URL??? ??????")
                ),
                responseFields(
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????? ?????????"),
                        fieldWithPath("data.payType").type(JsonFieldType.STRING)
                                .description(
                                        Stream.of(PayType.values()).map(PayType::name)
                                                .reduce((a, b) -> a + ", " + b).get()),
                        fieldWithPath("data.orderId").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.amount").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                        fieldWithPath("data.orderName").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.customerEmail").type(JsonFieldType.STRING).description("?????? ?????????"),
                        fieldWithPath("data.customerName").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.successUrl").type(JsonFieldType.STRING).description("?????? ????????? redirect ??? url"),
                        fieldWithPath("data.failUrl").type(JsonFieldType.STRING).description("?????? ????????? redirect ??? url"),
                        fieldWithPath("data.failReason").type(JsonFieldType.STRING).description("?????? ?????? err ????????? "),
                        fieldWithPath("data.cancelYN").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                        fieldWithPath("data.cancelReason").type(JsonFieldType.NULL).description("?????? ?????? ??????"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????")

                )
        ));

    }

    @Test
    @DisplayName("?????? ?????? ?????????")
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
                        parameterWithName("paymentKey").description("?????? ???"),
                        parameterWithName("amount").description("?????? ?????? ??????"),
                        parameterWithName("orderId").description("?????? ID"),
                        parameterWithName("_csrf").description("?????? ??? ??????")
                ),
                responseFields(
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????? ?????????"),
                        fieldWithPath("data.mid").type(JsonFieldType.STRING).description("????????? ?????????"),
                        fieldWithPath("data.version").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.paymentKey").type(JsonFieldType.STRING).description("?????? ???"),
                        fieldWithPath("data.orderId").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.orderName").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.currency").type(JsonFieldType.STRING).description("??????"),
                        fieldWithPath("data.method").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.totalAmount").type(JsonFieldType.STRING).description("?????? ??? ??????"),
                        fieldWithPath("data.balanceAmount").type(JsonFieldType.STRING).description("?????? ??? ??? ?????? ?????? ??????"),
                        fieldWithPath("data.suppliedAmount").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                        fieldWithPath("data.vat").type(JsonFieldType.STRING).description("??????"),
                        fieldWithPath("data.status").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.requestedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                        fieldWithPath("data.approvedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                        fieldWithPath("data.useEscrow").type(JsonFieldType.STRING).description("???????????? ?????? ??????"),
                        fieldWithPath("data.cultureExpense").type(JsonFieldType.STRING).description("????????? ??????"),
                        fieldWithPath("data.card").type(JsonFieldType.OBJECT).description("?????? ?????? ??????"),
                        fieldWithPath("data.card.company").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.card.number").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.card.installmentPlanMonths").type(JsonFieldType.STRING).description("?????? ?????? ???"),
                        fieldWithPath("data.card.isInterestFree").type(JsonFieldType.STRING).description("????????? ??????"),
                        fieldWithPath("data.card.approveNo").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.card.useCardPoint").type(JsonFieldType.STRING).description("?????? ????????? ?????? ??????"),
                        fieldWithPath("data.card.cardType").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.card.ownerType").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                        fieldWithPath("data.card.acquireStatus").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                        fieldWithPath("data.card.receiptUrl").type(JsonFieldType.STRING).description("?????? ????????? URL"),
                        fieldWithPath("data.type").type(JsonFieldType.STRING).description("?????? ??????")


                )
        ));
    }

    @Test
    void tossPaymentFail() {
    }

    @Test
    @DisplayName("?????? ?????? ??????")
    @WithMockUser
    void tossPaymentCancelPoint() throws Exception {
        // given
        String requestParams = "?paymentKey=" + createPaymentSuccessDto().getPaymentKey() + "&cancelReason=" + "?????? ??????";
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
                        parameterWithName("paymentKey").description("?????? ???"),
                        parameterWithName("cancelReason").description("?????? ??????"),
                        parameterWithName("_csrf").description("??????")
                )
        ));
    }

    @Test
    @DisplayName("?????? ????????? ?????? ?????? ??????")
    @WithMockUser(username = "test1@gmail.com", roles = "USER")
    void findChargingHistoriesTest() throws Exception {
        // given
        List<Payment> payments = PaymentFactory.createPaymentList();
        List<ChargingHistoryDto> responses = PaymentFactory.createChargingHistoryDtoList(payments);

        given(paymentService.findAllChargingHistories(any(), any())).willReturn(new SliceImpl<>(payments));
        given(mapper.chargingHistoryToChargingHistoryResponses(any())).willReturn(responses);

        // when
        ResultActions resultActions = ResultActionsUtils.getRequest(mockMvc, BASE_URL + "/history");
        resultActions.andExpect(status().isOk());

        // then
        resultActions.andDo(document("payment-findChargingHistories",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("JWT ??????")
                ),
                responseFields(
                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????? ??????"),
                        fieldWithPath("data[].paymentHistoryId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????????"),
                        fieldWithPath("data[].amount").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data[].orderName").type(JsonFieldType.STRING).description("????????? ?????? ( ex. ????????? ???????????? ?????? ??? ?????? <- ?????? ?????? ?????? ????????? ????????? ???????????? )"),
                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("????????? ??????"),
                        fieldWithPath("data[].paySuccessYN").type("Boolean").description("?????? ?????? ??????"),
                        fieldWithPath("sliceInfo").type(JsonFieldType.OBJECT).description("???????????? ??????"),
                        fieldWithPath("sliceInfo.getNumber").type(JsonFieldType.NUMBER).description("?????? ???????????? ??????"),
                        fieldWithPath("sliceInfo.getSize").type(JsonFieldType.NUMBER).description("?????? ???????????? ??????"),
                        fieldWithPath("sliceInfo.getNumberOfElements").type(JsonFieldType.NUMBER).description("?????? ??????????????? ????????? ?????? ????????? ???"),
                        fieldWithPath("sliceInfo.hasNext").type(JsonFieldType.BOOLEAN).description("?????? ??????????????? ?????? ??????"),
                        fieldWithPath("sliceInfo.hasPrevious").type(JsonFieldType.BOOLEAN).description("?????? ??????????????? ?????? ??????")
                )
        ));
    }

}