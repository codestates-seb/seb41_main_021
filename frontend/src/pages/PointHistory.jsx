import styled from 'styled-components';
import Header from '../components/Header';
import ChargeList from '../components/ChargeList';

export default function PointHistory() {
  return (
    <>
      <Container>
        <Header title="충전 내역" />
        <ChargeList date="오늘" price="10000원" />
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;
`;
