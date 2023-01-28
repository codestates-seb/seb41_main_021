import React, { useState, useEffect } from 'react';
import styled, { css } from 'styled-components';
import Input from '../common/Input';
import DestinationList from './DestinationList';

import { useDispatch, useSelector } from 'react-redux';
import { clearDestination, setDestination, setIsFilled } from '../../redux/slice/DestinationSlice';

export default function DestinationForm({ children }) {
  const des = useSelector(state => {
    return state.destination;
  });
  const dispatch = useDispatch();

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
          <>{children}</>
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
