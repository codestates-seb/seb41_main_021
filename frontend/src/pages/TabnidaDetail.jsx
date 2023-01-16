import styled from 'styled-components';
import Button from '../components/common/Button';
import NavBar from '../components/NavBar';
import Header from '../components/Header';

export default function TabnidaDetail() {
  return (
    <>
      <NavBar></NavBar>
      <Container>
        <Header title={'탑니다 상세'}></Header>
        <InfoContainer>
          <DateBox>
            <div>출발일: 2023년 1월 09일 (월)</div>
            <div>출발 시간: 14 : 00</div>
          </DateBox>
          <DestinationBox>
            <Departure>
              <Title>출발지</Title>
              <Address>서울역 3번출구</Address>
            </Departure>
            <Destination>
              <Title>도착지</Title>
              <Address>대전 서구 둔산동 345-4</Address>
            </Destination>
          </DestinationBox>
        </InfoContainer>
        <BottomContainer>
          <StopOverPassenger>
            <StopOver>
              <Heading>경유지</Heading>
              <Body>안산</Body>
            </StopOver>
            <StopOver>
              <Heading>탑승 인원</Heading>
              <Body>2명</Body>
            </StopOver>
          </StopOverPassenger>
          <SpecialNote>
            <SpecialHeading>특이 사항</SpecialHeading>
            <SpecialBody>짐이 허벌나게 많습니다 ㅠ.ㅠ</SpecialBody>
          </SpecialNote>
        </BottomContainer>
        <InviteBtn>초대하기</InviteBtn>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem;
`;
const InfoContainer = styled.div`
  width: 100%;
  height: 30%;
  background-color: #fafbfc;
  box-shadow: 0px 4px 4px -4px #a4a3a4;
  border-radius: 1rem;
`;
const DateBox = styled.div`
  width: 100%;
  height: auto;
  background-color: ${props => props.theme.colors.dark_blue};
  padding: 1rem 1rem 1rem 2rem;
  color: #fff;
  display: flex;
  flex-direction: column;
  box-shadow: 0px 4px 4px -4px #3f5179;

  div {
    font-size: 1.4rem;
    line-height: 2.2rem;
  }
`;
const DestinationBox = styled.div`
  width: 100%;
  height: 70%;
  padding: 1rem;
`;
const Departure = styled.div`
  width: 100%;
  height: 50%;
  padding: 1rem;
  display: flex;
  box-shadow: 0px 1px 1px -1px #a4a3a4;
`;
const Destination = styled.div`
  width: 100%;
  height: 50%;
  padding: 1rem;
  display: flex;
`;
const Title = styled.div`
  width: 30%;
  height: 100%;
  color: ${props => props.theme.colors.darker_blue};
  font-weight: bold;
  font-size: 1.5rem;
`;
const Address = styled.div`
  width: 100%;
  height: 100%;
  font-size: 1.5rem;
`;
const BottomContainer = styled.div`
  width: 100%;
  height: 40%;
  margin: 1rem;
  background-color: #fafbfc;
  box-shadow: 0px 4px 4px -4px #a4a3a4;
  border-radius: 1rem;
  padding: 2rem;
`;
const StopOverPassenger = styled.div`
  width: 100%;
  height: 30%;
  display: flex;
  justify-content: space-around;
`;
const StopOver = styled.div`
  width: 30%;
  height: 100%;
  border-radius: 1rem;
`;
const Heading = styled.div`
  width: 100%;
  height: 50%;
  box-shadow: 0px 3px 3px -3px #a4a3a4;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  color: ${props => props.theme.colors.darker_blue};
  font-weight: bold;
`;
const Body = styled.div`
  width: 100%;
  height: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.3rem;
  color: black;
  /* font-weight: bold; */
`;
const SpecialNote = styled.div`
  width: 100%;
  height: 60%;
  margin-top: 2rem;
`;
const SpecialHeading = styled(Heading)`
  height: 25%;
  color: ${props => props.theme.colors.main_blue};
`;
const SpecialBody = styled(Body)`
  height: 80%;
  line-height: 2rem;
`;
const InviteBtn = styled(Button)`
  width: 40%;
  margin-top: 1rem;
`;
