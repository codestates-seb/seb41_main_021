import styled from 'styled-components';
import Header from '../components/Header';
import ListItem from '../components/ListItem';

export default function JourneyList() {
  return (
    <>
      <Container>
        <Header title="여정 내역" />
        <ListItem date="오늘" journeyStart="내마음속" journeyEnd="너마음속" />
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;
`;
