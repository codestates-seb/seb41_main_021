import styled from 'styled-components';
import Header from '../components/Header';
import Input from '../components/common/Input';
import Button from '../components/common/Button';

export default function ExchagePage() {
  return (
    <>
      <Container>
        <Header title="포인트 환전하기" />
        <Contents>
          <Input label="거래 은행" placeholder="거래 은행 입력"></Input>
          <Input label="계좌 번호" placeholder='"-" 없이 입력'></Input>
          <Input label="환전 금액" placeholder="환전 금액 입력"></Input>
          <ExchangeBtn>환전하기</ExchangeBtn>
        </Contents>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;
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
const ExchangeBtn = styled(Button)`
  width: 100%;
  height: 3rem;
  margin: 4rem;
`;
