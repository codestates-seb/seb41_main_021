import styled from 'styled-components';
import Header from '../components/Header';
import Input from '../components/common/Input';
import Button from '../components/common/Button';
import { loadTossPayments } from '@tosspayments/payment-sdk';

export default function Purchase() {
  var clientKey = 'test_ck_OALnQvDd2VJno4oBOBaVMj7X41mN';
  var tossPayments = loadTossPayments(clientKey); // 클라이언트 키로 초기화하기
  // 이 아래 부분은 서버에 요청해서 가져올 내용
  const payment = () => {
    loadTossPayments(clientKey).then(tossPayments => {
      tossPayments
        .requestPayment('카드', {
          // 결제 수단 파라미터
          // 결제 정보 파라미터
          amount: 15000, // 결제 금액
          orderId: '1db00731-1e2d-464c-a35a-4e6389209427', // 각 서버에서 정한 고유한 주문 아이디
          orderName: '포인트 충전', // 상품명
          customerName: '박토스스',
          successUrl: 'http://localhost:8080/api/v1/payments/toss/success',
          failUrl: 'http://localhost:8080/api/v1/payments/toss/fail',
          customerEmail: 'test1@gmail.com',
        })
        .catch(function (error) {
          if (error.code === 'USER_CANCEL') {
            // 결제 고객이 결제창을 닫았을 때 에러 처리
          } else if (error.code === 'INVALID_CARD_COMPANY') {
            // 유효하지 않은 카드 코드에 대한 에러 처리
          }
        });
    });
  };
  return (
    <>
      <Header title="포인트 충전하기" />
      <Container>
        <Contents>
          <Input label="충전 금액" placeholder={'10,000'}></Input>
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
