import styled from 'styled-components';
import React from 'react';
import SimpleSlider from '../components/Carousel';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

export default function HomePage() {
  const [open, setOpen] = useState(false);
  const navigate = useNavigate();
  const login = () => {
    setOpen(!open);
    navigate('/login');
  };
  const signup = () => {
    setOpen(!open);
    navigate('/signup');
  };
  return (
    <>
      <Container>
        <LandingContainer>
          <SimpleSlider />
        </LandingContainer>
        <ButtonContainer>
          <button className="login-btn" onClick={login} open={open}>
            로그인
          </button>
          <button className="signup-btn" onClick={signup} open={open}>
            가입하기
          </button>
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
    cursor: pointer;
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
