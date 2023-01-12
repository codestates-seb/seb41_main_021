import styled from 'styled-components';
import Logo from '../images/Full_Logo.svg';

export default function Loading() {
  return (
    <>
      <Container>
        <img src={Logo} alt="logo"></img>
        <FontAwesomeIcon icon="fa-thin fa-spinner" />
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;

  img {
    width: 60%;
  }
`;
