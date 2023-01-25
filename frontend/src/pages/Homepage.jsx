import styled from 'styled-components';
import React from 'react';
import SimpleSlider from '../components/Carousel';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
// import { useTayoCreate } from '../hooks/useTayo';

export default function HomePage() {
  const navigate = useNavigate();
  const login = () => {
    navigate('/login');
  };
  const signup = () => {
    // navigate('/signup');
    // useTayoCreate('https://server.yata.kro.kr/api/v1/yata?yataStatus=nata', {
    //   title: 'test부산까지 같이가실 분~',
    //   specifics: '같이 노래들으면서 가요~',
    //   departureTime: '2023-01-18T09:16:10',
    //   timeOfArrival: '2023-01-18T09:16:10',
    //   maxWaitingTime: 20,
    //   maxPeople: 3,
    //   yataStatus: 'YATA_NEOTA',
    //   amount: 2000,
    //   carModel: 'bmw',
    //   strPoint: {
    //     longitude: 5.0,
    //     latitude: 4.0,
    //     address: '인천',
    //   },
    //   destination: {
    //     longitude: 3.0,
    //     latitude: 2.0,
    //     address: '부산',
    //   },
    // });
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
