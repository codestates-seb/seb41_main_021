import styled from 'styled-components';
import KakaoMap from '../KakaoMap';
import Button from '../common/Button';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import Header from '../Header';

import { MapMarker, Map } from 'react-kakao-maps-sdk';

export default function DestinationDetail() {
  const addressName = '서울 서초구 서초대로 지하 294',
    placeName = '교대역 3호선',
    x = 127.013867969161,
    y = 37.4927431676548;
  return (
    <>
      <Container>
        <Header title="맵 디테일" />
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
            <Button>등록하기</Button>
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
