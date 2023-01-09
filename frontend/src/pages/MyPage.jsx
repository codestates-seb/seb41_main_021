import styled from 'styled-components';
import NavBar from '../components/NavBar';
import profile from '../images/mypage/profile.png';
import oil from '../images/mypage/oil.png';
import help from '../images/mypage/help.png';
import carpool from '../images/mypage/carpool.png';
import like from '../images/mypage/like.png';
import arrow from '../images/mypage/arrow.png';

export default function MyPage() {
  return (
    <>
      <Container>
        <NavBar />
        <MyPageContainer>
          <ProfileContainer>
            <Profile>
              <img src={profile} alt="profileImage"></img>
              <Info>
                <div className="text">
                  <b>문재웅</b>
                </div>
                <div className="text">mjwoong</div>
                <div className="text">010-xxxx-xxxx</div>
                <div className="text">abcde@gmail.com</div>
              </Info>
            </Profile>
          </ProfileContainer>
          <OilContainer>
            <GrayBox>
              <OilLevel>
                <div className="level">
                  기름통 레벨<img src={help} alt="QuestionMark" width={10}></img>
                </div>
                <img src={oil} alt="oil" width={40}></img>
                <div className="liter"> 70L </div>
              </OilLevel>
              <RightBox>
                <TripNumber>
                  <img src={carpool} alt="car" width={35}></img>
                  <div className="number">30</div>
                  <div className="text">여정 횟수</div>
                </TripNumber>
                <Compliment>
                  <img src={like} alt="thumbsUp" width={35}></img>
                  <ComplimentText>
                    <div className="top">친절해요</div>
                    <div className="bottom">가장 많이 받은 칭찬</div>
                  </ComplimentText>
                </Compliment>
              </RightBox>
            </GrayBox>
          </OilContainer>
          <ListContainer>
            <JourneyRecord>
              <div className="text">여정 내역</div>
              <img src={arrow} alt="arrow" width={30}></img>
            </JourneyRecord>
            <JourneyRecord>
              <div className="text">여정 내역</div>
              <img src={arrow} alt="arrow" width={30}></img>
            </JourneyRecord>
            <JourneyRecord>
              <div className="text">여정 내역</div>
              <img src={arrow} alt="arrow" width={30}></img>
            </JourneyRecord>
          </ListContainer>
        </MyPageContainer>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;

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

const MyPageContainer = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
`;
const ProfileContainer = styled.div`
  width: 100%;
  height: 20%;
  background-color: #617db9;
  display: flex;
  align-items: center;
`;
const Profile = styled.div`
  width: 100%;
  height: 100%;
  padding: 0 7rem 0 7rem;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0px 4px 4px -4px #3f5179;
`;
const Info = styled.div`
  width: 60%;
  height: 100%;
  margin-left: 1rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  color: #fff;
  div {
    margin-top: 0.3rem;
  }
`;
const OilContainer = styled.div`
  width: 100%;
  height: 30%;
  box-shadow: 0px 2px 2px -2px #a4a3a4;
  padding: 5% 10% 5% 10%;
  display: flex;
  align-items: center;
  justify-content: center;
  justify-content: center;
`;
const GrayBox = styled.div`
  width: 100%;
  height: 90%;
  background-color: #f0f1f0;
  border-radius: 2rem;
  padding: 3rem;
  display: flex;
`;
const RightBox = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;
const OilLevel = styled.div`
  width: 55%;
  height: 100%;
  border-right: 1px solid black;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  padding: 1rem 2rem 1rem 0;
  .level {
    font-size: 0.8rem;
    color: gray;
    margin-bottom: 1rem;
  }
  .liter {
    font-size: 3rem;
    font-weight: 600;
    margin-top: 1rem;
  }
`;
const TripNumber = styled.div`
  width: 100%;
  height: 50%;
  padding-left: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
  .number {
    margin: 0 0.5rem 0 0.5rem;
    font-size: 2.5rem;
    font-weight: 600;
  }
  .text {
    font-size: 0.8rem;
    color: gray;
  }
`;
const Compliment = styled.div`
  width: 100%;
  height: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const ComplimentText = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  .top {
    font-size: 2rem;
    font-weight: 600;
    margin-bottom: 0.5rem;
  }
  .bottom {
    font-size: 0.8rem;
    color: gray;
  }
`;
const ListContainer = styled.div`
  width: 100%;
  height: 30%;
`;
const JourneyRecord = styled.div`
  width: 100%;
  height: 33.3%;
  box-shadow: 0px 2px 2px -2px #a4a3a4;
  padding: 0 3rem 0 3rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  .text {
    font-size: 1.5rem;
    font-weight: 600;
  }
`;
