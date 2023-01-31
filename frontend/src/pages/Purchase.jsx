import styled from 'styled-components';
import { useState } from 'react';
import Header from '../components/Header';
import Input from '../components/common/Input';
import Button from '../components/common/Button';
import { loadTossPayments } from '@tosspayments/payment-sdk';
import usePostData from '../hooks/usePostData';

export default function Purchase() {
  const [amount, setAmount] = useState('');
  var clientKey = 'test_ck_OALnQvDd2VJno4oBOBaVMj7X41mN';
  // 이 아래 부분은 서버에 요청해서 가져올 내용
  const payment = () => {
    const body = {
      payType: 'CARD',
      amount,
      orderName: '포인트 충전',
      yourSuccessUrl: 'http://localhost:3000/point-success',
      yourFailUrl: 'http://localhost:3000/point-fail',
    };
    usePostData('/api/v1/payments/toss', body).then(res => {
      const data = res.data.data;
      loadTossPayments(clientKey).then(tossPayments => {
        tossPayments
          .requestPayment(data.payType, {
            // 결제 수단 파라미터
            // 결제 정보 파라미터
            amount: data.amount, // 결제 금액
            orderId: data.orderId, // 각 서버에서 정한 고유한 주문 아이디
            orderName: data.orderName, // 상품명
            customerName: data.customerName,
            successUrl: data.successUrl,
            failUrl: data.failUrl,
            customerEmail: data.customerEmail,
          })
          .catch(function (error) {
            if (error.code === 'USER_CANCEL') {
              // 결제 고객이 결제창을 닫았을 때 에러 처리
            } else if (error.code === 'INVALID_CARD_COMPANY') {
              // 유효하지 않은 카드 코드에 대한 에러 처리
            }
          });
      });
    });
  };
  return (
    <>
      <Header title="포인트 충전하기" />
      <Container>
        <Contents>
          <Input label="충전 금액" placeholder={'최소 충전 금액: 1,000원'} state={amount} setState={setAmount}></Input>
          <AuthBtn onClick={payment}>Toss Pay로 충전하기</AuthBtn>
        </Contents>
      </Container>
    </>
  );
}

const Container = styled.div`
  flex: 1;
  overflow: scroll;
  display: flex;
  flex-direction: column;
  align-items: center;
`;
const Contents = styled.div`
  width: 80%;
  max-width: 600px;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 3rem;
  @media screen and (min-width: 800px) {
    margin-top: 1rem;
  }
`;
const AuthBtn = styled(Button)`
  width: 100%;
  height: 3rem;
  margin: 4rem;
`;
