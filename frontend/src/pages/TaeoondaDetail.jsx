import styled from 'styled-components';
import Button from '../components/common/Button';
import NavBar from '../components/NavBar';
import Header from '../components/Header';
import { IoIosArrowRoundForward, IoIosArrowForward } from 'react-icons/io';
import { BsCalendar4, BsPeople } from 'react-icons/bs';
import { BiWon, BiCommentDetail } from 'react-icons/bi';
import { VscAccount } from 'react-icons/vsc';
import { AiOutlineCar } from 'react-icons/ai';
import { useTayoRequest } from '../hooks/useTayo';
import { FiEdit, FiTrash } from 'react-icons/fi';
import { useNavigate } from 'react-router';

export default function TabnidaDetail() {
  const navigate = useNavigate();
  const requestHandler = () => {
    const data = {
      title: '태워주세욥',
      specifics: '애완견을 동반하고싶어요',
      departureTime: '2023-01-25T16:00:34',
      timeOfArrival: '2023-01-25T16:00:34',
      boardingPersonCount: 2,
      maxWaitingTime: 10,
      strPoint: {
        longitude: 2.5,
        latitude: 2.0,
        address: '강원도 원주시',
      },
      destination: {
        longitude: 2.5,
        latitude: 2.0,
        address: '강원도 원주시',
      },
    };

    useTayoRequest(`https://server.yata.kro.kr/api/v1/yata/apply/${yataId}`, data).then(res => {
      console.log(res);
    });
  };

  return (
    <>
      <Header title={'태웁니다'}></Header>
      <Container>
        <CRUDContainer>
          <FiEdit onClick={() => navigate('/taeoonda-edit')} />
          <FiTrash />
        </CRUDContainer>
        <ProfileContainer>
          <ProfileInfoContainer>
            <h2>작성자 정보</h2>
            <ProfileInfo>
              <VscAccount />
              <ProfileText>
                <div className="username">sepunjix0911</div>
                <div>기름통 레벨 70L</div>
              </ProfileText>
            </ProfileInfo>
          </ProfileInfoContainer>
          <IoIosArrowForward />
        </ProfileContainer>
        <InfoContainer>
          <JourneyContainer>
            <h2>출발지</h2>
            <IoIosArrowRoundForward />
            <h2>경유지</h2>
            <IoIosArrowRoundForward />
            <h2>도착지</h2>
          </JourneyContainer>
          <DateContainer>
            <BsCalendar4 />
            <p className="date-txt">2월 11일 토요일, 오후 5:00</p>
          </DateContainer>
          <PriceContainer>
            <BiWon />
            <p className="price-txt">10,000 원</p>
          </PriceContainer>
          <AmountContainer>
            <BsPeople />
            <p className="amount-txt">1/4 명</p>
          </AmountContainer>
          <CarContainer>
            <AiOutlineCar />
            <p className="car-txt">XM3</p>
          </CarContainer>
          <MemoContainer>
            <BiCommentDetail />
            <p className="memo-txt">흡연자여서 차에서 담배 냄새 날 수 있습니다.</p>
          </MemoContainer>
        </InfoContainer>

        <RequestButton onClick={requestHandler}>신청하기</RequestButton>
      </Container>
      <NavBar />
    </>
  );
}

const Container = styled.div`
  flex: 1;
  overflow: scroll;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const ProfileContainer = styled.div`
  width: 95%;
  margin-top: 1rem;
  padding: 1rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-radius: 0.3rem;
  background-color: #f6f6f6;
  color: #202020;

  svg {
    font-size: 1.2rem;
    margin-right: 0.5rem;
  }

  .username {
    font-weight: bold;
    margin-bottom: 0.3rem;
  }
`;

const ProfileInfoContainer = styled.div`
  display: flex;
  flex-direction: column;

  svg {
    font-size: 2rem;
  }
`;

const ProfileInfo = styled.div`
  margin: 1rem 0;
  display: flex;
  align-items: center;
`;

const ProfileText = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 0.3rem;
`;

const InfoContainer = styled.div`
  width: 90%;
  margin: 1rem 0;

  svg {
    font-size: 1.2rem;
  }

  h2 {
    font-size: 1.4rem;
  }

  p {
    font-size: 1.1rem;
    margin-left: 0.5rem;
  }

  div {
    display: flex;
    align-items: center;
    margin: 1.2rem 0;
  }
`;

const JourneyContainer = styled.div`
  padding: 0.5rem 0;
`;

const DateContainer = styled.div``;

const PriceContainer = styled.div``;

const AmountContainer = styled.div``;

const MemoContainer = styled.div``;

const CarContainer = styled.div``;

const RequestButton = styled(Button)`
  width: 40%;
  margin-top: 1rem;
`;

const CRUDContainer = styled.div`
  width: 95%;
  margin-top: 1rem;
  display: flex;
  align-items: center;
  justify-content: flex-end;

  svg {
    font-size: 1.7rem;
    margin: 0.5rem 0.8rem;
    cursor: pointer;
  }
`;
