import styled from 'styled-components';
import Header from '../components/Header';

export default function DriverAuth() {
  return (
    <>
      <Container>
        <Header title="운전자 인증하기" />
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;
`;
