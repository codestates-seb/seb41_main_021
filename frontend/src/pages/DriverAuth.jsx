import styled from 'styled-components';
import Header from '../components/Header';
import Input from '../components/common/Input';
import Button from '../components/common/Button';

export default function DriverAuth() {
  return (
    <>
      <Header title="운전자 인증하기" />
      <Container>
        <Contents>
          <Input label="이름"></Input>
          <Input label="운전면허 번호"></Input>
          <Input label="발급일자"></Input>
          <AuthBtn>인증하기</AuthBtn>
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
