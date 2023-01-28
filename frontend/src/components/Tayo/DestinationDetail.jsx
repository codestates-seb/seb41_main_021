import styled from 'styled-components';
import KakaoMap from '../KakaoMap';
import Button from '../common/Button';
import { useState } from 'react';
import Header from '../Header';
import { useNavigate, useSearchParams } from 'react-router-dom';

import { MapMarker, Map } from 'react-kakao-maps-sdk';
import { useDispatch } from 'react-redux';
import { addDestination, setDestination, setIsFilled } from '../../redux/slice/DestinationSlice';

export default function DestinationDetail() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [SearchParams, setSearchParams] = useSearchParams();
  const addressName = SearchParams.get('address'),
    placeName = SearchParams.get('place'),
    x = SearchParams.get('x'),
    y = SearchParams.get('y');

  const add = () => {
    dispatch(
      addDestination({
        destinationPoint: {
          longitude: x,
          latitude: y,
          address: placeName,
        },
        isDestination: true,
      }),
    );

    dispatch(setDestination({ destination: placeName }));
    dispatch(setIsFilled({ setIsFilled: false }));
    navigate('/tabnida-add');
  };

  return (
    <>
      <Container>
        <Header title={placeName} />
        <MapContainer>
          <Map // 지도를 표시할 Container
            center={{
              // 지도의 중심좌표
              lat: y,
              lng: x,
            }}
            style={{
              // 지도의 크기
              width: '100%',
              height: '100%',
            }}
            level={4} // 지도의 확대 레벨
          >
            <MapMarker // 마커를 생성합니다
              position={{
                // 마커가 표시될 위치입니다
                lat: y,
                lng: x,
              }}
            />
          </Map>
        </MapContainer>
        <DestinationForm>
          <TextContainer>
            <AddressName>{placeName}</AddressName>
            <PlaceName>{addressName}</PlaceName>
          </TextContainer>
          <ButtonContainer>
            <Button onClick={add}>등록하기</Button>
          </ButtonContainer>
        </DestinationForm>
      </Container>
    </>
  );
}
const Container = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
`;

const MapContainer = styled.div`
  width: 100%;
  flex: 1;
`;

const DestinationForm = styled.div`
  padding: 2rem 2rem;
  width: 100%;
  height: auto;
  box-shadow: 0px -10px 10px -10px lightgrey;
  background-color: white;
  border-radius: 10% 10% 0 0;
  overflow: scroll;

  @media only screen and (min-width: 470px) {
    width: 470px;
  }

  @media screen and (min-height: 470px) {
    height: auto;
  }
`;

const TextContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
`;

const AddressName = styled.span`
  margin-bottom: 0.5rem;
`;
const PlaceName = styled.span`
  color: ${props => props.theme.colors.gray};
`;

const ButtonContainer = styled.div`
  display: flex;
  align-items: center;

  button {
    flex: 1;
  }
`;
