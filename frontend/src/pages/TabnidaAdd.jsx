import { useState, useEffect } from 'react';
import styled, { css } from 'styled-components';
import KakaoMap from '../components/KakaoMap';
import Input from '../components/common/Input';
import Button from '../components/common/Button';
import DestinationList from '../components/Tayo/DestinationList';
import Header from '../components/Header';
import { AiOutlinePlusCircle } from 'react-icons/ai';

export default function TabnidaAdd() {
  const [isFilled, setIsFilled] = useState(false);
  const [departure, setDeparture] = useState('');
  const [destination, setDestination] = useState('');
  const [isDeparture, setIsDeparture] = useState(false);
  const [isDestination, setIsDestination] = useState(false);
  const [Places, setPlaces] = useState([]);

  useEffect(() => {
    if (departure === '') {
      setIsDeparture(false);
    }
    if (destination === '') {
      setIsDestination(false);
    }
    //임시용
    if (departure !== '' && destination !== '') {
      setIsDeparture(true);
      setIsDestination(true);
    } else {
      setIsDeparture(false);
      setIsDestination(false);
    }

    if (departure === '' && destination === '') {
      setIsFilled(false);
    }
  }, [departure, destination]);

  return (
    <>
      <Container>
        <Header title="탑니다 등록하기" />
        <div className="map-container">
          <KakaoMap searchPlace={departure || destination} setPlaces={setPlaces} setDeparture={setDeparture} />
        </div>
        <DestinationForm isFilled={isFilled}>
          <Input
            label="출발지"
            placeholder="출발지 입력"
            state={departure}
            setState={setDeparture}
            onFocus={() => setIsFilled(true)}
          />
          <div className="destinationInput">
            <Input
              label="도착지"
              placeholder="도착지 입력"
              state={destination}
              setState={setDestination}
              onFocus={() => setIsFilled(true)}
            />
            <AiOutlinePlusCircle className="plus-icon" />
          </div>
          {isFilled &&
            (isDeparture && isDestination ? (
              <>
                <Input label="출발 일시" type="datetime-local" />
                <Input label="인당 금액" type="number" placeholder="인당 금액 입력" />
                <Input label="탑승 인원" type="number" min="1" max="10" placeholder="1" />
                <Input label="특이사항" placeholder="아이가 있어요, 흡연자입니다, 짐이 많아요, 등" />
                <ButtonContainer>
                  <Button className="register-btn">등록하기</Button>
                </ButtonContainer>
              </>
            ) : (
              <>
                <DestinationList Places={Places} />
              </>
            ))}
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
  overflow: hidden;

  .map-container {
    width: 100%;
    flex: 1;
  }
`;

const DestinationForm = styled.div`
  padding: 2rem 3rem;
  width: 100%;
  height: auto;
  box-shadow: 0px -10px 10px -10px lightgrey;
  background-color: white;
  border-radius: 10% 10% 0 0;
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
  /* 
  @media screen and (min-height: 470px) {
    height: 100%;
  } */

  .destinationInput {
    position: relative;
  }

  .destinationInput svg {
    position: absolute;
    top: 3rem;
    right: 1rem;
    font-size: 1.7rem;
    padding: 0.2rem;
    color: #6f6f6f;

    @media only screen and (min-width: 470px) {
      top: 0.8rem;
      right: 1rem;
    }
  }

  .plus-icon {
    cursor: pointer;
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
