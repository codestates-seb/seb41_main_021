import styled from 'styled-components';
import KakaoMap from '../components/KakaoMap';
import Input from '../components/common/Input';
import Button from '../components/common/Button';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { AiOutlinePlusCircle } from 'react-icons/ai';
import Header from '../components/Header';

export default function TaeoondaEdit() {
  // const navigate = useNavigate();
  // const next = () => {
  //   navigate('/tabnidaadddetail');
  // };

  const [num, setNum] = useState(1);

  const handleSubmit = e => {
    console.log('hi');
  };

  return (
    <>
      <Container>
        <Header title="태웁니다 수정하기" />
        <div className="map-container">
          <KakaoMap />
        </div>
        <DestinationForm>
          <Input label="출발지" />
          <div className="destinationInput">
            <Input label="도착지" />
            <AiOutlinePlusCircle className="plus-icon" onClick={handleSubmit} />
          </div>
          <Input label="출발 일시" type="datetime-local" />
          <Input label="탑승 인원" type="number" min="1" max="10" state={num} />
          <Input label="특이사항" />
          <ButtonContainer>
            <Button
              className="register-btn"
              // onClick={next}
            >
              수정하기
            </Button>
          </ButtonContainer>
        </DestinationForm>
      </Container>
    </>
  );
}
const Container = styled.div`
  width: 100%;
  height: 100vh;

  .map-container {
    width: 100%;
    height: 50%;
  }
`;

const DestinationForm = styled.div`
  padding: 2rem 3rem;
  width: 100%;
  height: 40%;
  position: absolute;
  bottom: 0;
  z-index: 1;
  box-shadow: 0px -10px 10px -10px lightgrey;
  background-color: white;
  border-radius: 10% 10% 0 0;
  overflow: scroll;

  @media only screen and (min-width: 470px) {
    width: 470px;
  }

  @media screen and (min-height: 470px) {
    height: 58%;
  }

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
