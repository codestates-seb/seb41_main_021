import { useState, useEffect } from 'react';
import styled from 'styled-components';
import KakaoMap from '../components/KakaoMap';
import Header from '../components/Header';
import DestinationForm from '../components/Tayo/DestinationForm';
import DestinationInputForm from '../components/Tayo/DestinationInputForm';

import { useSelector } from 'react-redux';

import { useTayoEdit } from '../hooks/useTayo';

import { useNavigate, useParams } from 'react-router-dom';

export default function TabnidaEdit() {
  const des = useSelector(state => {
    return state.destination;
  });

  const params = useParams();
  const yataId = params.yataId;
  const navigate = useNavigate();

  const editTabnida = () => {
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

    useTayoEdit(`/api/v1/yata/${yataId}`, data).then(res => {
      navigate(`/tabnida-detail/${yataId}`);
    });
  };
  return (
    <>
      <Header title="탑니다 수정하기" />
      <Container>
        <div className="map-container">
          <KakaoMap />
        </div>
        <DestinationForm>
          <DestinationInputForm submit={editTabnida} />
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
