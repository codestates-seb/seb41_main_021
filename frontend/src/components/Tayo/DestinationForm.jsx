import React, { useState, useEffect } from 'react';
import styled, { css } from 'styled-components';
import Button from '../common/Button';
import { useNavigate } from 'react-router-dom';
import { useTayoCreate } from '../../hooks/useTayo';
import Input from '../common/Input';
import DestinationList from './DestinationList';

import { useDispatch, useSelector } from 'react-redux';
import { clearDestination, setDestination, setIsFilled } from '../../redux/slice/DestinationSlice';

export default function DestinationForm() {
  const des = useSelector(state => {
    return state.destination;
  });
  const navigate = useNavigate();
  const [departureTime, setDepartureTime] = useState('');
  const [amount, setAmount] = useState('');
  const [maxPeople, setMaxPeople] = useState('');
  const [specifics, setSpecifics] = useState('');

  const dispatch = useDispatch();

  const createTabnida = () => {
    const data = {
      title: '제목',
      specifics,
      departureTime,
      timeOfArrival: '2100-12-23T22:38:28',
      maxWaitingTime: 0,
      maxPeople,
      yataStatus: 'YATA_NATA',
      amount,
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

    useTayoCreate('https://server.yata.kro.kr/api/v1/yata', data).then(res => {
      console.log(res);
      navigate('/tabnida-list');
    });
  };
  useEffect(() => {
    if (des.destination === '') {
      dispatch(clearDestination());
    } else if (des.departure === '' && des.destination === '') {
      dispatch(setIsFilled({ isFilled: false }));
    }
  }, [des.departure, des.destination]);

  const departureHan = e => {
    dispatch(setDestination({ destination: e.target.value }));
  };

  return (
    <>
      <Container isFilled={des.isFilled}>
        <Input label="출발지" placeholder="출발지 입력" state={des.departure} readOnly={true} />
        <div className="destinationInput">
          <Input
            label="도착지"
            placeholder="도착지 입력"
            state={des.destination}
            onChange={departureHan}
            onFocus={() => dispatch(setIsFilled({ isFilled: true }), dispatch(clearDestination()))}
          />
        </div>
        {/* 이 부분 컴포넌트 화 해서 태웁니다랑 구분, useTayoCreate, useTayoEdit 구분 */}
        {des.isDeparture && des.isDestination ? (
          <>
            <Input label="출발 일시" type="datetime-local" state={departureTime} setState={setDepartureTime} />
            <Input label="인당 금액" type="number" placeholder="인당 금액 입력" state={amount} setState={setAmount} />
            <Input
              label="탑승 인원"
              type="number"
              min="1"
              max="10"
              placeholder="1"
              state={maxPeople}
              setState={setMaxPeople}
            />
            <Input
              label="특이사항"
              placeholder="아이가 있어요, 흡연자입니다, 짐이 많아요, 등"
              state={specifics}
              setState={setSpecifics}
            />
            <ButtonContainer>
              <Button className="register-btn" onClick={createTabnida}>
                등록하기
              </Button>
            </ButtonContainer>
          </>
        ) : (
          <>
            <DestinationList places={des.places} />
          </>
        )}
      </Container>
    </>
  );
}

const Container = styled.div`
  padding: 2rem 3rem;
  width: 100%;
  height: auto;
  box-shadow: 0px -10px 10px -10px lightgrey;
  background-color: white;
  overflow: scroll;
  ${props =>
    props.isFilled &&
    css`
      height: 100%;
      box-shadow: none;
      border-radius: 0;
    `}

  @media only screen and (min-width: 470px) {
    width: 470px;
  }

  .destinationInput svg {
    font-size: 1.7rem;
    padding: 0.2rem;
    color: #6f6f6f;

    @media only screen and (min-width: 470px) {
      top: 0.8rem;
      right: 1rem;
    }
  }
`;

const TransitContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-end;
  margin-top: 1rem;

  svg {
    padding: 0.2rem;
  }

  p {
    cursor: pointer;
    font-size: 1rem;
  }
`;

const TransitField = styled.div`
  display: flex;
  align-items: center;
`;

const TransitInput = styled(Input)``;

const ButtonContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-end;

  button {
    margin: 10px 0;
  }
`;

const DeleteButton = styled.button`
  margin: 1.5rem 0 0 0.5rem;
  background-color: white;
  color: black;
  cursor: pointer;

  :hover {
    background-color: none;
  }

  :active {
    background-color: none;
  }
`;
