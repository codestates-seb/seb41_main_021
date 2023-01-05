import styled from 'styled-components';
import React from 'react';
import NavBar from '../components/NavBar';
import mainLogo from '../images/mainLogo.png';

export default function HomePage() {
  return (
    <>
      <Container>
        <NavBar />
        <ContentContainer>
          <LogoBox>
            <img src={mainLogo} alt="mainLogo"></img>
          </LogoBox>
          <GuideBox>
            <div className="head">이용 가이드</div>
            <div>야타를 처음 이용해보시나요?</div>
          </GuideBox>
        </ContentContainer>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;

  // 태블릿 : 1200px ~ 768px :: 768px 이상 적용되는 css
  @media only screen and (min-width: 768px) {
    width: 100%;
    height: 100vh;
    /* background-color: red; */
  }
  // PC : 1200px 이상 :: 1200px 이상 적용되는 css
  @media only screen and (min-width: 1200px) {
    width: 100%;
    height: 100vh;
    /* background-color: blue; */
  }
`;

const ContentContainer = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`
const LogoBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 15.625rem;
  height: 15.625rem;
`;

const GuideBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 15rem;
  height: 7.5rem;
  margin-top: 10rem;
  font-size: 0.8rem;
  /* color: #73b2d9; */

  .head {
    font-size: 1rem;
    font-weight: 700;
    margin-bottom: 1rem;
  }
`;
