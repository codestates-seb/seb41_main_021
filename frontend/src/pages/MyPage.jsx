import styled from 'styled-components';
import NavBar from '../components/NavBar';
import { NavLink } from 'react-router-dom';
import { VscAccount } from 'react-icons/vsc';
import { BiTrip, BiLike } from 'react-icons/bi';
import { RiOilLine } from 'react-icons/ri';
import { IoIosArrowForward } from 'react-icons/io';
import { GrMoney } from 'react-icons/gr';

export default function MyPage() {
  return (
    <>
      <Container>
        <NavBar />
        <MyPageContainer>
          <ProfileContainer>
            <Profile>
              <VscAccount />
              <Info>
                <div className="text">
                  <b>문재웅</b>
                </div>
                <div className="text">mjwoong</div>
                <div className="text">010-xxxx-xxxx</div>
                <div className="text">abcde@gmail.com</div>
              </Info>
            </Profile>
            <PointContainer>
              <div className="point-text-container">
                <GrMoney />
                <div className="point-text">포인트</div>
              </div>
              <div className="point-amount">5,000원</div>
            </PointContainer>
          </ProfileContainer>
          <SummaryContainer>
            <TripNumber>
              <BiTrip />
              <div className="title">여정 횟수</div>
              <div className="bottom">30</div>
            </TripNumber>
            <OilLevel>
              <RiOilLine />
              <div className="title">기름통 레벨</div>
              <div className="bottom"> 70L </div>
            </OilLevel>
            <Compliment>
              <BiLike />
              <div className="title">많이 받은 칭찬</div>
              <div className="bottom">친절해요</div>
            </Compliment>
          </SummaryContainer>
          <ListContainer>
            <JourneyRecord>
              <div className="title">포인트 충전하기</div>
              <IoIosArrowForward />
            </JourneyRecord>
            <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/driver-auth">
              <JourneyRecord>
                <div className="title">운전자 인증하기</div>
                <IoIosArrowForward />
              </JourneyRecord>
            </NavLink>
            <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/journey-list">
              <JourneyRecord>
                <div className="title">여정 내역</div>
                <IoIosArrowForward />
              </JourneyRecord>
            </NavLink>
            <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/rating-list">
              <JourneyRecord>
                <div className="title">받은 매너 평가</div>
                <IoIosArrowForward />
              </JourneyRecord>
            </NavLink>
          </ListContainer>
        </MyPageContainer>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;
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
  height: 100%;
  background-color: ${props => props.theme.colors.main_blue};
  box-shadow: 0px 4px 4px -4px #3f5179;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  @media only screen and (max-height: 667px) {
    height: 35%;
  }
`;
const Profile = styled.div`
  width: 100%;
  height: 40%;
  padding: 0 7rem 0 7rem;
  position: relative;
  bottom: 3rem;
  display: flex;
  align-items: center;

  svg {
    font-size: 7rem;
    color: #26264c;
    background-color: white;
    padding: 10px;
    border-radius: 1rem;
    box-shadow: 0px 4px 4px -4px #3f5179;
  }

  @media only screen and (max-height: 667px) {
    height: 30%;
  }
`;
const Info = styled.div`
  width: 60%;
  height: 100%;
  margin-left: 1rem;
  display: flex;
  flex-direction: column;
  /* color: #fff; */
  div {
    margin-top: 0.3rem;
    font-size: 1.2rem;
  }
`;

const PointContainer = styled.div`
  width: 80%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  font-weight: bold;

  svg {
    font-size: 1.2rem;
    margin-right: 0.3rem;
  }

  .point-text-container {
    display: flex;
    align-items: center;
  }
`;

const SummaryContainer = styled.div`
  width: 90%;
  height: 12rem;
  background-color: #fff;
  border-radius: 1rem;
  box-shadow: 0 0px 5px lightgray;
  position: relative;
  bottom: 5rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-around;

  .title {
    color: ${props => props.theme.colors.main_blue};
    font-weight: bold;
    margin: 0.5rem 0 0.5rem 0;
  }
  .bottom {
    color: ${props => props.theme.colors.dark_gray};
    font-size: 1.2rem;
    font-weight: 600;
  }

  svg {
    font-size: 8rem;
    color: #26264c;
  }

  div {
    width: 7rem;
    height: 7rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-evenly;
  }
`;

const OilLevel = styled.div``;
const TripNumber = styled.div``;
const Compliment = styled.div``;

const ListContainer = styled.div`
  width: 100%;
  height: auto;
  box-shadow: 0px -2px 2px -2px ${props => props.theme.colors.light_gray};
  overflow: scroll;
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
