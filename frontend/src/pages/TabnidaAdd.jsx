import styled from 'styled-components';
import KakaoMap from '../components/KakaoMap';
import Header from '../components/Header';
import DestinationForm from '../components/Tayo/DestinationForm';
import DestinationInputForm from '../components/Tayo/DestinationInputForm';

import { useTayoCreate } from '../hooks/useTayo';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { clearAll } from '../redux/slice/DestinationSlice';
export default function TabnidaAdd() {
  const des = useSelector(state => {
    return state.destination;
  });
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const createTabnida = () => {
    const data = {
      title: '제목',
      specifics: des.specifics,
      departureTime: des.departureTime,
      timeOfArrival: '2100-12-23T22:38:28',
      maxWaitingTime: 0,
      maxPeople: des.maxPeople,
      yataStatus: 'YATA_NATA',
      amount: des.amount,
      carModel: '차종',
      strPoint: {
        longitude: des.departurePoint.longitude,
        latitude: des.departurePoint.latitude,
        address: des.departurePoint.address,
      },
      destination: {
        longitude: des.destinationPoint.longitude,
        latitude: des.destinationPoint.latitude,
        address: des.destinationPoint.address,
      },
    };
    console.log(data);
    useTayoCreate('https://server.yata.kro.kr/api/v1/yata', data).then(res => {
      console.log(res);
      navigate('/tabnida-list');
      dispatch(clearAll());
    });
  };
  return (
    <>
      <Header title="탑니다 등록하기" />
      <Container>
        <div className="map-container">
          <KakaoMap />
        </div>
        <DestinationForm>
          <DestinationInputForm submit={createTabnida} />
        </DestinationForm>
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
