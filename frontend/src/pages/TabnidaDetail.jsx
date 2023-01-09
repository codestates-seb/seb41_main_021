import styled from 'styled-components';
import Button from '../components/common/Button';

export default function TabnidaDetail() {
  return (
    <>
      <Container>
        <p>출발지</p>
        <p>경유지</p>
        <p>도착지</p>
        <p>출발시간</p>
        <p>탑승 인원</p>
        <p>특이사항</p>

        <Button>초대하기</Button>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  background-color: pink;
`;
