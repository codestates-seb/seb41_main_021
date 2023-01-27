import styled from 'styled-components';
import NavBar from '../components/NavBar';
import { NavLink } from 'react-router-dom';
import { VscAccount } from 'react-icons/vsc';
import { BiTrip, BiLike } from 'react-icons/bi';
import { RiOilLine } from 'react-icons/ri';
import { IoIosArrowForward } from 'react-icons/io';
import { RiCoinsFill } from 'react-icons/ri';

import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { checkIfLogined } from '../hooks/useLogin';
import { useDispatch, useSelector } from 'react-redux';
import { clearUser } from '../redux/slice/UserSlice';
import useGetData from '../hooks/useGetData';

export default function MyPage() {
  const [review, setReview] = useState([]);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const logout = () => {
    localStorage.clear();
    dispatch(clearUser());
    navigate('/');
  };

  const info = useSelector(state => {
    return state.user;
  });

  const isLogin = checkIfLogined();

  useEffect(() => {
    if (!isLogin) {
      return navigate('/');
    }
    useGetData(`https://server.yata.kro.kr/api/v1/review/${info.email}`).then(res => {
      if (res.status === 200) {
        setReview(res.data.data);
      } else {
        console.log('리뷰 정보를 가져오는데 실패하였습니다.');
      }
    });
  }, []);
  return (
    <>
      <Container>
        <MyPageContainer>
          <ProfileContainer>
            <Profile>
              <VscAccount />
              <Info>
                <div className="text">{info.name}</div>
                <div className="text">{info.nickname}</div>
                <div className="text">{info.email}</div>
              </Info>
            </Profile>
          </ProfileContainer>
          <SummaryContainer>
            <OilLevel>
              <RiOilLine />
              <div className="title">기름통 레벨</div>
              <div className="bottom"> {info.fuelTank}L </div>
            </OilLevel>
            <TripNumber>
              <BiTrip />
              <div className="title">여정 횟수</div>
              <div className="bottom">30</div>
            </TripNumber>
            <Compliment>
              <BiLike />
              <div className="title">많이 받은 칭찬</div>
              <div className="bottom">{review.length === 0 && '리뷰가 없음'}</div>
            </Compliment>
          </SummaryContainer>
          <PointContainer>
            <div className="point-text-container">
              <div className="point-text">
                <RiCoinsFill />
                포인트
              </div>
              <div className="point-amount">{info.point}원</div>
            </div>
            <div
              className="buy-point"
              onClick={() => {
                navigate('/purchase');
              }}>
              <div className="title">포인트 충전하기</div>
              <IoIosArrowForward />
            </div>
          </PointContainer>
          <ListContainer>
            <List>
              <ListTitle>포인트 관리</ListTitle>
              <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/point-history">
                <JourneyRecord>
                  <div className="title">충전 내역</div>
                  <IoIosArrowForward />
                </JourneyRecord>
              </NavLink>
              <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/exchange-page">
                <JourneyRecord>
                  <div className="title">환전하기</div>
                  <IoIosArrowForward />
                </JourneyRecord>
              </NavLink>
            </List>
            <List>
              <ListTitle>탑니다/태웁니다 관리</ListTitle>
              <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/journey-history">
                <JourneyRecord>
                  <div className="title">나의 지난 여정 내역</div>
                  <IoIosArrowForward />
                </JourneyRecord>
              </NavLink>
              <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/my-register-history">
                <JourneyRecord>
                  <div className="title">나의 신청 내역</div>
                  <IoIosArrowForward />
                </JourneyRecord>
              </NavLink>
              <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/journey-history">
                <JourneyRecord>
                  <div className="title">내가 작성한 글</div>
                  <IoIosArrowForward />
                </JourneyRecord>
              </NavLink>
            </List>
            <List>
              <ListTitle>일반</ListTitle>
              <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/driver-auth">
                <JourneyRecord>
                  <div className="title">운전자 인증하기</div>
                  <IoIosArrowForward />
                </JourneyRecord>
              </NavLink>
              <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/rating-list">
                <JourneyRecord>
                  <div className="title">받은 매너 평가</div>
                  <IoIosArrowForward />
                </JourneyRecord>
              </NavLink>

              <JourneyRecord onClick={logout}>
                <div className="title">로그아웃</div>
                <IoIosArrowForward />
              </JourneyRecord>
            </List>
          </ListContainer>
        </MyPageContainer>
      </Container>
      <NavBar />
    </>
  );
}

const Container = styled.div`
  flex: 1;
  overflow: scroll;
`;

const MyPageContainer = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: scroll;
`;
const ProfileContainer = styled.div`
  width: 80%;
  height: auto;
  margin: 3rem 0 2rem;
`;

const Profile = styled.div`
  width: 80%;
  display: flex;
  align-items: center;

  svg {
    margin-right: 2rem;
    font-size: 5rem;
    border-radius: 1rem;
  }
`;
const Info = styled.div`
  display: flex;
  flex-direction: column;

  .text {
    font-size: 1.1rem;
  }
`;

const PointContainer = styled.div`
  width: 90%;
  padding: 2rem;
  margin: 1rem 0 2rem 0;
  background-color: ${props => props.theme.colors.main_blue};
  color: white;
  border-radius: 1rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  font-weight: bold;

  svg {
    color: white;
    font-size: 1.2rem;
    margin-right: 0.3rem;
  }

  .point-text-container {
    width: 100%;
    padding: 0.5rem;
    margin-bottom: 1rem;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .point-text {
    display: flex;
    align-items: center;
    font-size: 1.6rem;

    svg {
      font-size: 1.6rem;
    }
  }

  .point-amount {
    font-size: 1.6rem;
  }

  .buy-point {
    width: 100%;
    padding: 2rem 0.5rem 0;
    display: flex;
    align-items: center;
    justify-content: flex-start;
    margin-top: 1rem;
    border-top: 0.9px solid white;

    div {
      cursor: pointer;
      font-size: 1.2rem;
    }
  }
`;

const SummaryContainer = styled.div`
  width: 90%;
  margin: 1rem 0;
  background-color: #fff;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-around;

  .title {
    color: ${props => props.theme.colors.main_blue};
    font-size: 1.1rem;
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
  margin-bottom: 60px;
`;

const List = styled.div`
  width: 100%;
  border-top: 5px solid #f6f6f6;
`;

const ListTitle = styled.h1`
  margin: 1rem 0;
  padding: 1rem 3rem 0 3rem;
  font-size: 1.5rem;
  font-weight: bold;
`;

const JourneyRecord = styled.div`
  width: 100%;
  height: 5rem;
  padding: 0 3rem 0 3rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #f6f6f6;

  .title {
    font-size: 1.2rem;
  }
`;
