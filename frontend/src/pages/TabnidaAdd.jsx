import styled from 'styled-components';
import KakaoMap from '../components/KakaoMap';
import Header from '../components/Header';
import DestinationForm from '../components/Tayo/DestinationForm';

export default function TabnidaAdd() {
  return (
    <>
      <Header title="탑니다 등록하기" />
      <Container>
        <div className="map-container">
          <KakaoMap />
        </div>
        <DestinationForm />
      </Container>
    </>
  );
}
const Container = styled.div`
  flex: 1;
  overflow: scroll;
  display: flex;
  flex-direction: column;
  overflow: hidden;

  .map-container {
    width: 100%;
    flex: 1;
  }
`;
