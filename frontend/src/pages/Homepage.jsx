import styled from 'styled-components';
import React from 'react';
import SimpleSlider from '../components/Carousel';

export default function HomePage() {
  return (
    <>
      <Container>
        <LandingContainer>
          <SimpleSlider />
        </LandingContainer>
        <ButtonContainer>
          <button className="login-btn">로그인</button>
          <button className="signup-btn">가입하기</button>
        </ButtonContainer>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;
`;

const LandingContainer = styled.div`
  width: 100%;
  height: 90%;
`;

const ButtonContainer = styled.div`
  width: 100%;
  height: 10vh;
  display: flex;

  button {
    all: unset;
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.5rem;
    font-weight: bold;
  }

  .login-btn {
    background-color: #4c5c7a;
    color: white;
  }

  .signup-btn {
    background-color: ${({ theme }) => theme.colors.main_blue};
    color: white;
  }
`;
