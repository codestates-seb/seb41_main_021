import styled from 'styled-components';
import NavBar from '../components/NavBar';
import { NavLink } from 'react-router-dom';
import { VscAccount } from 'react-icons/vsc';
import { BiTrip, BiLike } from 'react-icons/bi';
import { RiOilLine } from 'react-icons/ri';
import { IoIosArrowForward } from 'react-icons/io';
import { useNavigate } from 'react-router-dom';
import Button from '../components/common/Button.jsx';
import Header from '../components/Header';
import { useState, useEffect } from 'react';
import { useGetData } from '../hooks/useGetData';
import { useParams } from 'react-router-dom';
import defaultProf from '../images/Logo.svg';

export default function OtherUserPage() {
  const navigate = useNavigate();
  const params = useParams();
  const email = params.email;

  const [loading, setLoading] = useState(true);
  const [data, setData] = useState([]);
  const [review, setReview] = useState([]);

  useEffect(() => {
    useGetData(`/api/v1/members/${email}`).then(res => setData(res.data.data, setLoading(false)));
  }, []);

  useEffect(() => {
    useGetData(`/api/v1/review/${email}`).then(res => setReview(res.data.data, setLoading(false)));
  }, []);

  return (
    <>
      <Header title={data.nickname} />
      <Container>
        <MyPageContainer>
          <ProfileContainer>
            <Profile>
              {data.imgUrl === null ? (
                <ProfPic src={defaultProf} alt="profile picture" className="profile" />
              ) : (
                <ProfPic src={data.imgUrl} alt="profile picture" className="profile" />
              )}
              <Info>
                <div className="text">{data.name}</div>
                <div className="text">{data.nickname}</div>
                <div className="text">{data.email}</div>
              </Info>
            </Profile>
          </ProfileContainer>
          <SummaryContainer>
            <OilLevel>
              <RiOilLine />
              <div className="title">기름통 레벨</div>
              <div className="bottom"> {data.fuelTank}L </div>
            </OilLevel>
            <TripNumber>
              <BiTrip />
              <div className="title">여정 횟수</div>
              <div className="bottom">{data.yataCount}</div>
            </TripNumber>
            <Compliment>
              <BiLike />
              <div className="title">많이 받은 칭찬</div>
              <div className="bottom">{review.length === 0 && '리뷰가 없음'}</div>
            </Compliment>
          </SummaryContainer>
          <ListContainer>
            {/* <List>
              <ListTitle>탑니다/태웁니다 관리</ListTitle>
              <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to="/journey-list">
                <JourneyRecord>
                  <div className="title">여정 내역</div>
                  <IoIosArrowForward />
                </JourneyRecord>
              </NavLink>
            </List> */}
            <List>
              <ListTitle>일반</ListTitle>
              <NavLink className={({ isActive }) => (isActive ? 'active' : 'not')} to={`/rating-list/${email}`}>
                <JourneyRecord>
                  <div className="title">받은 매너 평가</div>
                  <IoIosArrowForward />
                </JourneyRecord>
              </NavLink>
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

  button {
    margin-bottom: 2rem;
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
      font-size: 1.2rem;
    }
  }
`;

const SummaryContainer = styled.div`
  width: 90%;
  margin: 2rem 0;
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
  overflow: scroll;
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

const ProfPic = styled.img`
  width: 7rem;
  padding: 0.2rem;
  margin-right: 1.5rem;
  border-radius: 1rem;
`;
