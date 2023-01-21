import styled from 'styled-components';
import Header from '../components/Header';
import Navbar from '../components/NavBar';

export default function JourneyState() {
  return (
    <>
      <Header />
      <Container></Container>
      <Navbar />
    </>
  );
}

const Container = styled.div`
  flex: 1;
  overflow: scroll;
`;
