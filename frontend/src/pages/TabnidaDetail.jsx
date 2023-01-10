import styled from 'styled-components';
import Button from '../components/common/Button';
import NavBar from '../components/NavBar';

export default function TabnidaDetail() {
  return (
    <>
      <NavBar></NavBar>
      <Container>
        <InfoContainer>
          <DateBox>
            <div>출발일: 2023년 1월 09일 (월)</div>
          </DateBox>
          <DestinationBox>
            <Departure>
              <div>서울</div>
              <div className="time">14 : 00</div>
            </Departure>
            <div>→</div>
            <Destination>
              <div>대전</div>
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
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem;
`;
const InfoContainer = styled.div`
  width: 100%;
  height: 25%;
  background-color: #fafbfc;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-shadow: 0px 4px 4px -4px #a4a3a4;
  border-radius: 1rem;
`;
const DateBox = styled.div`
  width: 100%;
  height: 15%;
  background-color: ${props => props.theme.colors.dark_blue};
  padding: 1rem 1rem 1rem 1rem;
  color: #fff;
  display: flex;
  align-items: center;
  box-shadow: 0px 4px 4px -4px #3f5179;
  div {
    font-size: 1.2rem;
  }
`;
const DestinationBox = styled.div`
  width: 100%;
  height: 50%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  div {
    font-size: 2rem;
    color: ${props => props.theme.colors.darker_blue};
  }
`;
const Departure = styled.div`
  width: 40%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  div {
    color: ${props => props.theme.colors.darker_blue};
    font-weight: bold;
    position: relative;
    top: 1.2rem;
  }
  .time {
    font-size: 1.5rem;
    margin-top: 1rem;
    color: ${props => props.theme.colors.main_blue};
  }
`;
const Destination = styled.div`
  width: 40%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  div {
    color: ${props => props.theme.colors.darker_blue};
    font-weight: bold;
  }
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
  font-weight: bold;
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
