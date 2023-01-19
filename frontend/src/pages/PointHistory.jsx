import styled from 'styled-components';
import Header from '../components/Header';
import ChargeList from '../components/ChargeList';

export default function PointHistory() {
  return (
    <>
      <Header title="충전 내역" />
      <Container>
        <ChargeList date="오늘" price="10000원" />
      </Container>
    </>
  );
}

const Container = styled.div`
  flex: 1;
  overflow: scroll;
`;
