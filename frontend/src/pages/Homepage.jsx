import styled from 'styled-components';
import React from 'react';
import NavBar from '../components/NavBar';
import mainImg from '../images/car.png';
import brandname from '../images/name.png';

export default function HomePage() {
  return (
    <>
      <Container>
        <NavBar />
        <ContentContainer>
          <Main>
            <TextContainer>
              <h3>카쉐어링 서비스</h3>
              <img src={brandname} alt="brand name" />
              <p>
                언제 어디서나 안심하고 <br />
                사용할 수 있는 카 쉐어링 서비스
              </p>
            </TextContainer>
            <img src={mainImg} alt="3d car" />
          </Main>
        </ContentContainer>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: auto;
`;

const ContentContainer = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  background: linear-gradient(147deg, #ffe8ee, #80b1d5);
`;
const Main = styled.div`
  width: 80%;
  padding: 10px;
  display: flex;
  flex-direction: row-reverse;
  align-items: center;
  justify-content: center;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 15px;

  img {
    width: 150px;
  }
`;

const TextContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;

  h3 {
    font-size: 15px;
    margin-left: 8px;
  }

  p {
    color: gray;
    font-size: 10px;
    margin-left: 8px;
  }

  img {
    width: 100px;
  }
`;
