import styled from 'styled-components';
import Header from '../components/Header';
import Input from '../components/common/Input';
import Button from '../components/common/Button';

export default function Purchase() {
  return (
    <>
      <Header title="신청하기" />
      <Container>
        <Contents>
          <Input label="탑승 인원" type="number" min="1" max="10" placeholder="1" />
          <Input label="특이사항" placeholder="아이가 있어요, 흡연자입니다, 짐이 많아요, 등" />
          <AuthBtn>신청하기</AuthBtn>
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
