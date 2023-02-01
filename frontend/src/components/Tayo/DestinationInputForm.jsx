import React from 'react';
import styled from 'styled-components';
import Button from '../common/Button';
import Input from '../common/Input';

import { useDispatch, useSelector } from 'react-redux';
import {
  setAmount,
  setDepartureTime,
  setMaxPeople,
  setSpecifics,
  setCarModel,
} from '../../redux/slice/DestinationSlice';
export default function DestinationInputForm({ submit, taeoonda }) {
  const des = useSelector(state => {
    return state.destination;
  });

  const dispatch = useDispatch();

  const departureTimeHan = e => {
    dispatch(setDepartureTime({ departureTime: e.target.value }));
  };
  const amountHan = e => {
    dispatch(setAmount({ amount: e.target.value }));
  };
  const maxPeopleHan = e => {
    dispatch(setMaxPeople({ maxPeople: e.target.value }));
  };
  const specificsHan = e => {
    dispatch(setSpecifics({ specifics: e.target.value }));
  };
  const carModelHan = e => {
    dispatch(setCarModel({ carModel: e.target.value }));
  };

  return (
    <>
      <Container>
        {/* 이 부분 컴포넌트 화 해서 태웁니다랑 구분, useTayoCreate, useTayoEdit 구분 */}
        <Input label="출발 일시" type="datetime-local" state={des.departureTime} onChange={departureTimeHan} />
        <Input
          label="인당 금액"
          type="number"
          placeholder="인당 금액 입력"
          min="0"
          step="100"
          state={des.amount}
          onChange={amountHan}
        />
        {taeoonda ? (
          <Input
            label="탑승가능 인원"
            type="number"
            min="1"
            max="10"
            placeholder="1"
            state={des.maxPeople}
            onChange={maxPeopleHan}
          />
        ) : (
          <Input
            label="탑승 인원"
            type="number"
            min="1"
            max="10"
            placeholder="1"
            state={des.maxPeople}
            onChange={maxPeopleHan}
          />
        )}
        {taeoonda && (
          <Input
            label="차종"
            type="text"
            placeholder="XM3, 싼타페, 그랜저 IG, 등"
            state={des.carModel}
            onChange={carModelHan}
          />
        )}
        <Input
          label="특이사항"
          placeholder="아이가 있어요, 흡연자입니다, 짐이 많아요, 등"
          state={des.specifics}
          onChange={specificsHan}
        />
        <ButtonContainer>
          <Button onClick={submit} className="register-btn">
            등록하기
          </Button>
        </ButtonContainer>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: auto;

  @media only screen and (min-width: 470px) {
    top: 0.8rem;
    right: 1rem;
  }
`;

const ButtonContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-end;

  button {
    margin: 10px 0;
  }
`;
