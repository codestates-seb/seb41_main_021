import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Header from '../components/Header';
import { useGetData } from '../hooks/useGetData';
import { IoIosArrowForward, IoIosArrowRoundForward } from 'react-icons/io';
import { BsCalendar4, BsPeople } from 'react-icons/bs';
import { BiWon } from 'react-icons/bi';
import { dateFormat } from '../components/common/DateFormat';
import { useParams } from 'react-router-dom';

export default function RegisterList() {
  const navigate = useNavigate();
  const params = useParams();
  const yataId = params.yataId;

  const [list, setList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    useGetData(`/api/v1/yata/myYatas/myRequested`).then(res => {
      setList(res.data.data);
      setLoading(false);
    });
  }, []);

  const shortWords = (str, length = 10) => {
    let result = '';
    if (str.length > length) {
      result = str.substr(0, length) + '...';
    } else {
      result = str;
    }
    return result;
  };

  return (
    <>
      <Header title="내가 받은 신청" />
      {list.length !== 0 && !loading ? (
        <Container>
          {list.map(el => {
            return (
              <TextContainer
                key={el.yataId}
                onClick={() => {
                  navigate(`/register-checklist/${el.yataId}`);
                }}>
                <DateContainer>
                  <BsCalendar4 />
                  {dateFormat(el.departureTime)}
                  {console.log(el.yataStatus)}
                  {/* {yataRequestStatus && (
                    <YataRequestTagContainer yataRequestStatus={yataRequestStatus}>
                      {yataRequestStatus === 'APPLY' ? '신청' : '초대'}
                    </YataRequestTagContainer>
                  )} */}
                </DateContainer>
                <JourneyContainer>
                  <JourneyText>
                    {shortWords(el.strPoint.address)}
                    <IoIosArrowRoundForward />
                    {shortWords(el.destination.address)}
                  </JourneyText>
                  <IoIosArrowForward />
                </JourneyContainer>
                <BottomContainer>
                  <PriceContainer>
                    <BiWon />
                    {el.amount.toLocaleString('ko-KR')}원
                  </PriceContainer>
                  <PeopleContainer>
                    <BsPeople />
                    {el.reservedMemberNum}/{el.maxPeople}
                  </PeopleContainer>
                </BottomContainer>
              </TextContainer>
            );
          })}
        </Container>
      ) : (
        <Container>
          <div className="no-content">받은 신청 내역이 없습니다.</div>
        </Container>
      )}
      <Navbar />
    </>
  );
}

const Container = styled.div`
  flex: 1;
  overflow: scroll;
  display: flex;
  flex-direction: column;
  align-items: center;

  .no-content {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.5rem;
  }
`;

const TextContainer = styled.div`
  border-bottom: 1px solid #f6f6f6;
  width: 100%;
  padding: 1rem;
  display: flex;
  justify-content: center;
  flex-direction: column;
  svg {
    margin-right: 0.5rem;
  }
`;

const DateContainer = styled.div`
  display: flex;
  align-items: center;
  color: ${props => props.theme.colors.gray};
  font-weight: bold;
  margin-bottom: 0.8rem;
`;

const TagContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 0.5rem;
  padding: 0.5rem;
  height: 1.5rem;
  color: white;
  border-radius: 0.2rem;
  font-size: 0.9rem;

  /* background-color: ${props =>
    props.state === '대기'
      ? props.theme.colors.gray
      : props.state === '수락'
      ? props.theme.colors.main_blue
      : props.theme.colors.light_red}; */
`;

const JourneyContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 0.7rem;

  svg {
    color: ${props => props.theme.colors.gray};
    font-size: 1.5rem;
  }
`;

const JourneyText = styled.span`
  display: flex;
  align-items: center;
  font-size: 1.5rem;
  font-weight: bold;
  svg {
    color: ${props => props.theme.colors.gray};
    font-size: 2rem;
    margin: 0 0.5rem;
  }
`;

const BottomContainer = styled.div`
  display: flex;
  flex-direction: row;
`;
const PriceContainer = styled.div`
  margin-right: 2rem;
  color: ${props => props.theme.colors.dark_gray};
  svg {
    position: relative;
    top: 0.1rem;
  }
`;
const PeopleContainer = styled.div`
  color: ${props => props.theme.colors.dark_gray};
  svg {
    position: relative;
    top: 0.1rem;
  }
`;

const YataRequestTagContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 0.5rem;

  width: 4rem;
  height: 1.1rem;
  color: white;
  border-radius: 0.2rem;
  font-size: 0.7rem;

  background-color: ${props => (props.yataRequestStatus === 'APPLY' ? '#7c92c0' : '#7c7cc0')};
`;
