import React, { useState, useEffect } from 'react';
import styled, { css } from 'styled-components';
import KakaoMap from '../components/KakaoMap';
import Input from '../components/common/Input';
import Button from '../components/common/Button';
import DestinationList from '../components/Tayo/DestinationList';
import Header from '../components/Header';
import { BsPlusLg } from 'react-icons/bs';
import { TiDeleteOutline } from 'react-icons/ti';

export default function TaeoondaEdit() {
  const [isFilled, setIsFilled] = useState(false);
  const [departure, setDeparture] = useState('');
  const [destination, setDestination] = useState('');
  const [isDeparture, setIsDeparture] = useState(false);
  const [isDestination, setIsDestination] = useState(false);
  const [Places, setPlaces] = useState([]);

  const [inputFields, setInputFields] = useState([
    {
      fullName: '',
    },
  ]);

  const addInputField = () => {
    setInputFields([
      ...inputFields,
      {
        fullName: '',
      },
    ]);
  };
  const removeInputFields = index => {
    const rows = [...inputFields];
    rows.splice(index, 1);
    setInputFields(rows);
  };
  const handleChange = (index, evnt) => {
    const { name, value } = evnt.target;
    const list = [...inputFields];
    list[index][name] = value;
    setInputFields(list);
  };

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
      <Header title="태웁니다 수정하기" />
      <Container>
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

          {inputFields.map((data, index) => {
            return (
              <TransitField key={index}>
                <TransitInput onChange={event => handleChange(index, event)} label="경유지" placeholder="경유지 입력" />
                {inputFields.length !== 1 ? (
                  <DeleteButton onClick={removeInputFields}>
                    <TiDeleteOutline />
                  </DeleteButton>
                ) : (
                  ''
                )}
              </TransitField>
            );
          })}

          <TransitContainer onClick={addInputField}>
            <BsPlusLg />
            <p>경유지 추가</p>
          </TransitContainer>

          <div className="destinationInput">
            <Input
              label="도착지"
              placeholder="도착지 입력"
              state={destination}
              setState={setDestination}
              onFocus={() => setIsFilled(true)}
            />
          </div>
          {isFilled &&
            (isDeparture && isDestination ? (
              <>
                <Input label="출발 일시" type="datetime-local" />
                <Input label="인당 금액" type="number" placeholder="인당 금액 입력" />
                <Input label="탑승 인원" type="number" min="1" max="10" placeholder="1" />
                <Input label="차종" type="text" placeholder="XM3, 싼타페, 그랜저 IG, 등" />
                <Input label="특이사항" placeholder="아이가 있어요, 흡연자입니다, 짐이 많아요, 등" />
                <ButtonContainer>
                  <Button className="register-btn">수정하기</Button>
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

const DestinationForm = styled.div`
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
