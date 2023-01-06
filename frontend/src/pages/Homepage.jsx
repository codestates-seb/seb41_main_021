import styled from 'styled-components';
import React from 'react';
import NavBar from '../components/NavBar';
import Footer from '../components/Footer';
import backgroundImg from '../images/Landing_page.webp';
import mainImg from '../images/car.png';
import wallet from '../images/wallet.png';
import heartwithhands from '../images/heartwithhands.png';
import location from '../images/location_ping.png';
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
          <Sub>
            <div>
              <img src={wallet} alt="3d wallet" />
              <h3>Affordable</h3>
              <p>택시비보다 저렴한 가격으로</p>
            </div>
            <div>
              <img src={heartwithhands} alt="3d heart with hands" />
              <h3>Safe</h3>
              <p>마음 편히 이용 가능한 안심 서비스</p>
            </div>
            <div>
              <img src={location} alt="3d location ping" />
              <h3>Convenient</h3>
              <p>언제 어디서나 출발하고 도착</p>
            </div>
          </Sub>
        </ContentContainer>
        <Footer />
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: auto;

  // 태블릿 : 1200px ~ 768px :: 768px 이상 적용되는 css
  @media only screen and (min-width: 768px) {
    width: 100%;
    height: 100vh;
  }
  // PC : 1200px 이상 :: 1200px 이상 적용되는 css
  @media only screen and (min-width: 1200px) {
    width: 100%;
    height: 100%;
  }
`;

const ContentContainer = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  background: linear-gradient(147deg, #ffe8ee, #80b1d5);

  @media only screen and (min-width: 768px) {
    height: auto;
    background-image: url(${backgroundImg});
    background-repeat: no-repeat;
    background-size: cover;
    background-position: top 60px center;
  }
  // PC : 1200px 이상 :: 1200px 이상 적용되는 css
  @media only screen and (min-width: 1200px) {
  }
`;
const Main = styled.div`
  width: 80%;
  height: 1000px;
  margin-top: 130px;
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

  p {
    overflow: visible;
  }

  @media only screen and (min-width: 768px) {
    margin-top: 180px;
    background-color: rgba(255, 255, 255, 0);
    height: 100%;

    img {
      width: 300px;
    }

    @media only screen and (min-width: 1200px) {
      img {
        width: 400px;
      }
    }
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

  @media only screen and (min-width: 768px) {
    margin-left: 20px;
    h3 {
      font-size: 15px;
      margin-left: 8px;
    }

    p {
      font-size: 15px;
      margin-left: 8px;
    }

    img {
      width: 200px;
    }
  }

  @media only screen and (min-width: 1200px) {
    margin-top: 30px;
    margin-left: 50px;
    h3 {
      font-size: 20px;
      margin-left: 8px;
    }

    p {
      font-size: 20px;
      margin-left: 8px;
    }

    img {
      width: 300px;
    }
  }
`;

const Sub = styled.div`
  width: 80%;
  height: 400px;
  margin-bottom: 100px;
  display: flex;
  align-items: center;
  justify-content: space-evenly;

  img,
  h3,
  p {
    display: none;
  }

  div {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  @media only screen and (min-width: 768px) {
    img {
      display: block;
      width: 140px;
      margin-top: 50px;
    }

    h3 {
      display: block;
      margin: 10px;
    }

    p {
      display: block;
      margin: 10px;
      font-size: 13px;
    }
  }
  // PC : 1200px 이상 :: 1200px 이상 적용되는 css
  @media only screen and (min-width: 1200px) {
    img {
      display: block;
      width: 150px;
      margin-top: 100px;
    }
    h3 {
      display: block;
      margin: 10px;
      margin-top: 20px;
      font-size: 30px;
    }

    p {
      display: block;
      margin: 10px;
      font-size: 20px;
    }
  }
`;
