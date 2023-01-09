import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Button from '../components/common/Button';
import ListItem from '../components/ListItem';
import DestinationInput from '../components/DestinationInput';

export default function RegisterDetail() {
  return (
    <>
      <Container>
        <p>유저 정보</p>
        <p>출발지</p>
        <p>경유지</p>
        <p>도착지</p>
        <p>출발시간</p>
        <p>탑승 인원</p>
        <p>특이사항</p>
        <Button>수락하기</Button>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  background-color: pink;
`;
