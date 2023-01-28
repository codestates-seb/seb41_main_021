import styled from 'styled-components';
import Header from '../components/Header';
import Navbar from '../components/NavBar';
import JourneyItem from '../components/Journey/JourneyItem';
import HelpContainer from '../components/Journey/HelpContainer';
import { useState, useEffect } from 'react';
import { useGetData } from '../hooks/useGetData';
import { dateFormat } from '../components/common/DateFormat';

export default function JourneyState() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    useGetData(`https://server.yata.kro.kr/api/v1/yata/myYataRequests`).then(res => {
      if (res.data.data.approvalStatus === 'ACCEPTED') {
        setData(res.data.data, setLoading(false));
      } else {
        return;
      }
    });
  }, []);

  return (
    <>
      <Header title="여정 현황" />
      <Container>
        <HelpContainer>결제하기를 누르면, 자동으로 포인트가 차감됩니다.</HelpContainer>
        {data.map(el => {
          return (
            <JourneyItem
              key={el.yataId}
              yataId={el.yataId}
              date={dateFormat(el.departureTime)}
              journeyStart={el.strPoint.address}
              journeyEnd={el.destination.address}
              price={el.amount}
              people={`1/${el.maxPeople}`}
              state={'대기'}
              isPay={false}
            />
          );
        })}
        <JourneyItem
          key={123123123}
          date={'2023-01-21'}
          journeyStart={'안산'}
          journeyEnd={'의왕'}
          transit={'1'}
          price={5000}
          people={'1/4'}
          state={'대기'}
          isPay={false}
        />
        <JourneyItem
          key={151}
          date={'2023-01-21'}
          journeyStart={'안산'}
          journeyEnd={'의왕'}
          transit={'1'}
          price={5000}
          people={'1/4'}
          state={'도착'}
          isPay={true}
        />
      </Container>
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
`;
