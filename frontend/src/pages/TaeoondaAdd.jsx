import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import KakaoMap from '../components/KakaoMap';
import Header from '../components/Header';
import DestinationForm from '../components/Tayo/DestinationForm';
import DestinationInputForm from '../components/Tayo/DestinationInputForm';

import { useTayoCreate } from '../hooks/useTayo';
import { useDispatch, useSelector } from 'react-redux';
import { clearAll } from '../redux/slice/DestinationSlice';

export default function TaeoondaAdd() {
  const des = useSelector(state => {
    return state.destination;
  });
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const createTaeoonda = () => {
    const data = {
      title: '제목',
      specifics: des.specifics,
      departureTime: des.departureTime,
      timeOfArrival: '2100-12-31T22:38:28',
      maxWaitingTime: 0,
      maxPeople: des.maxPeople,
      yataStatus: 'YATA_NEOTA',
      amount: des.amount,
      carModel: des.carModel,
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

    useTayoCreate('https://server.yata.kro.kr/api/v1/yata?yataStatus=nata', data).then(res => {
      console.log(res);
      navigate(`/taeoonda-list`);
      dispatch(clearAll());
    });
  };

  return (
    <>
      <>
        <Header title="태웁니다 등록하기" />
        <Container>
          <div className="map-container">
            <KakaoMap taeoonda={true} />
          </div>
          <DestinationForm>
            <DestinationInputForm submit={createTaeoonda} taeoonda={true} />
          </DestinationForm>
        </Container>
      </>
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
