import styled from 'styled-components';
import Header from '../components/Header';
import Navbar from '../components/NavBar';
import JourneyItemList from '../components/Journey/JourneyItemList';
import JourneyItem from '../components/Journey/JourneyItem';
import HelpContainer from '../components/Journey/HelpContainer';

export default function JourneyState() {
  return (
    <>
      <Header title="여정 현황" />
      <Container>
        <HelpContainer>결제하기를 누르면, 자동으로 포인트가 차감됩니다.</HelpContainer>
        {/* <JourneyItemList /> */}
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
          key={123123123}
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
