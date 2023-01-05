import styled from 'styled-components';
import React from 'react';
import NavBar from '../components/NavBar';
import Footer from '../components/Footer'
import backgroundImg from '../images/Landing_page.webp'
import mainImg from '../images/car.png'
import wallet from '../images/wallet.png'
import heartwithhands from '../images/heartwithhands.png'
import location from '../images/location_ping.png'

export default function HomePage() {
  return (
    <>
      <Container>
        <NavBar />
        <ContentContainer>
          <Main>
            <TextContainer>
              <h3>카쉐어링 서비스</h3>
              <h1>YATA</h1>
              <p>언제 어디서나 안심하고 <br/> 
              사용할 수 있는 카 쉐어링 서비스</p>
            </TextContainer>
            <img src={mainImg} alt="3d car"/>
          </Main>
          <Sub>
            <div>
              <img src={wallet} alt="3d wallet"/>
              <h3>Affordable</h3>
              <p>택시비보다 저렴한 가격으로</p>
            </div>
            <div>
              <img src={heartwithhands} alt="3d heart with hands"/>
              <h3>Safe</h3>
              <p>마음 편히 이용 가능한 안심 서비스</p>
            </div>
            <div>
              <img src={location} alt="3d location ping"/>
              <h3>Convenient</h3>
              <p>언제 어디서나 출발하고 도착</p>
            </div>
          </Sub>
          {/* <Footer/> */}
        </ContentContainer>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100vh;
  
  // 태블릿 : 1200px ~ 768px :: 768px 이상 적용되는 css
  @media only screen and (min-width: 768px) {
    width: 100%;
    height: 100vh;
  }
  // PC : 1200px 이상 :: 1200px 이상 적용되는 css
  @media only screen and (min-width: 1200px) {
    width: 100%;
    height: 100vh;
  }
`;

const ContentContainer = styled.div`
  background-image: url(${backgroundImg});
  background-repeat: no-repeat;
  background-size: cover;
  background-position: top 60px center;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
`
const Main = styled.div`
  width: 80%;
  height: 400px;
  margin-top: 180px;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  /* background-color: blue; */
  
  img {
    width: 400px;
  }
  `

const TextContainer = styled.div`

p {
  color: gray;
}
`

const Sub = styled.div`
  width: 80%;
  height: 400px;
  margin-bottom: 100px;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  /* background-color: red; */

  img {
    width: 150px;
    margin: 10px;
  }  

  div {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  h3 {
    margin: 10px;
  }

  p {
    margin: 10px;
  }
`