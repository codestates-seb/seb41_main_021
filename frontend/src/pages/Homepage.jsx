import styled from 'styled-components';
import React from 'react';
import SimpleSlider from '../components/Carousel';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

export default function HomePage() {
  const navigate = useNavigate();
  const login = () => {
    navigate('/login');
  };
  const signup = () => {
    navigate('/signup');
  };
  return (
    <>
      <Container>
        <LandingContainer>
          <SimpleSlider />
        </LandingContainer>
        <ButtonContainer>
          <button
            className="login-btn"
            onClick={() => {
              navigate('/login');
            }}>
            로그인
          </button>
          <button
            className="signup-btn"
            onClick={() => {
              navigate('/signup');
            }}>
            가입하기
          </button>
        </ButtonContainer>
      </Container>
    </>
  );
}

const Container = styled.div`
  flex: 1;
  overflow: hidden;
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
