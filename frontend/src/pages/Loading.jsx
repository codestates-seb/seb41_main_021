import styled from 'styled-components';
import Logo from '../images/Logo.svg';

export default function Loading() {
  return (
    <>
      <Container>
        <LogoBox>
          <img src={Logo} alt="logo"></img>
        </LogoBox>
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

  // 태블릿 : 1200px ~ 768px :: 768px 이상 적용되는 css
  @media only screen and (min-width: 768px) {
    background-color: red;
  }
  // PC : 1200px 이상 :: 1200px 이상 적용되는 css
  @media only screen and (min-width: 1200px) {
    background-color: blue;
  }
`;
const LogoBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 15.625rem;
  height: 15.625rem;
`;
