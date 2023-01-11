import styled from 'styled-components';
import NavBar from '../components/NavBar';
import profile from '../images/mypage/profile.png';
import oil from '../images/mypage/oil.png';
import help from '../images/mypage/help.png';
import carpool from '../images/mypage/carpool.png';
import like from '../images/mypage/like.png';
import arrow from '../images/mypage/arrow.png';
import { NavLink } from 'react-router-dom';

export default function MyPage() {
  return (
    <>
      <Container>
        <NavBar />
        <MyPageContainer>
          <ProfileContainer>
            <Profile>
              <img src={profile} alt="profileImage" width={100} height={100}></img>
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
            <TripNumber>
              <img src={carpool} alt="car" width={35} height={35}></img>
              <div className="title">여정 횟수</div>
              <div className="bottom">30</div>
            </TripNumber>
            <OilLevel>
              <img src={oil} alt="oil" width={35} height={35}></img>
              <div className="title">기름통 레벨</div>
              <div className="bottom"> 70L </div>
            </OilLevel>
            <Compliment>
              <img src={like} alt="thumbsUp" width={35} height={35}></img>
              <div className="title">많이 받은 칭찬</div>
              <div className="bottom body">친절해요</div>
            </Compliment>
          </OilContainer>
          <ListContainer>
            <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/journeylist">
              <JourneyRecord>
                <div className="title">여정 내역</div>
                <img src={arrow} alt="arrow" width={30}></img>
              </JourneyRecord>
            </NavLink>
            <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/ratinglist">
              <JourneyRecord>
                <div className="title">받은 매너 평가</div>
                <img src={arrow} alt="arrow" width={30}></img>
              </JourneyRecord>
            </NavLink>
            <JourneyRecord>
              <div className="title">-</div>
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
  align-items: center;
`;
const ProfileContainer = styled.div`
  width: 100%;
  height: 25%;
  background-color: ${props => props.theme.colors.main_blue};
  box-shadow: 0px 4px 4px -4px #3f5179;
`;
const Profile = styled.div`
  width: 100%;
  height: 100%;
  padding: 0 7rem 0 7rem;
  position: relative;
  bottom: 3rem;
  display: flex;
  align-items: center;
  justify-content: center;
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
    font-size: 1.2rem;
  }
`;
const OilContainer = styled.div`
  width: 90%;
  height: 12rem;
  background-color: #fff;
  border-radius: 1rem;
  box-shadow: 0 0px 10px #73b2d9, 0 0px 10px #73b2d9;
  position: relative;
  bottom: 5rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-around;
  .title {
    font-weight: 600;
    margin: 0.5rem 0 0.5rem 0;
    color: ${props => props.theme.colors.main_blue};
  }
  .bottom {
    color: ${props => props.theme.colors.gray};
    font-size: 1.8rem;
    font-weight: 600;
  }
`;
const OilLevel = styled.div`
  width: 7rem;
  height: 7rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;
const TripNumber = styled.div`
  width: 7rem;
  height: 7rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;
const Compliment = styled.div`
  width: 7rem;
  height: 7rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  bottom: 0.2rem;
  .body {
    color: ${props => props.theme.colors.gray};
    font-size: 1.4rem;
    font-weight: 600;
  }
`;
const ListContainer = styled.div`
  width: 100%;
  height: auto;
  box-shadow: 0px -2px 2px -2px ${props => props.theme.colors.light_gray};
`;
const JourneyRecord = styled.div`
  width: 100%;
  height: 6rem;
  box-shadow: 0px 2px 2px -2px ${props => props.theme.colors.light_gray};
  padding: 0 3rem 0 3rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  .title {
    font-size: 1.3rem;
    font-weight: 600;
  }
`;
