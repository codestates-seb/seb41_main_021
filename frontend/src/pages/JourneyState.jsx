import styled from 'styled-components';
import Header from '../components/Header';
import Navbar from '../components/NavBar';
import JourneyItem from '../components/Journey/JourneyItem';
import HelpContainer from '../components/Journey/HelpContainer';
import { useState, useEffect } from 'react';
import useGetData from '../hooks/useGetData';
import useJourneyState from '../hooks/useJourneyState';
import { dateFormat } from '../components/common/DateFormat';

export default function JourneyState() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isUpdate, setIsUpdate] = useState(true);

  useEffect(() => {
    if (isUpdate) {
      useJourneyState('https://server.yata.kro.kr/api/v1/yata/accept/yatas').then(res => {
        setData(res.data.data);
        setIsUpdate(false);
      });
    }
  }, [isUpdate]);

  return (
    <>
      <Header title="확정된 여정" />
      {data.length !== 0 ? (
        <Container>
          <HelpContainer>결제하기를 누르면, 자동으로 포인트가 차감됩니다.</HelpContainer>
          {data.map(el => {
            return (
              <JourneyItem
                key={el.yataResponse.yataId}
                yataId={el.yataResponse.yataId}
                yataMemberId={el.yataResponse.yataMembers[0].yataMemberId}
                date={dateFormat(el.yataResponse.departureTime)}
                journeyStart={el.yataResponse.strPoint.address}
                journeyEnd={el.yataResponse.destination.address}
                price={el.yataResponse.amount}
                people={`${el.yataResponse.reservedMemberNum}/${el.yataResponse.maxPeople}`}
                isPay={el.yataPaid}
                setIsUpdate={setIsUpdate}
              />
            );
          })}
        </Container>
      ) : (
        <Container>
          <div className="no-content">여정 현황이 없습니다.</div>
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

  svg {
    position: static;
  }

  .no-content {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.5rem;
  }
`;
