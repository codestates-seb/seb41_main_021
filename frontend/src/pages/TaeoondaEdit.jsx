import styled from 'styled-components';
import KakaoMap from '../components/KakaoMap';
import Input from '../components/common/Input';
import Button from '../components/common/Button';
import Header from '../components/Header';
import { useState } from 'react';
import { AiOutlinePlusCircle } from 'react-icons/ai';

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
        <Header />
        <KakaoMap />
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
  display: flex;
  flex-direction: column;
  z-index: 1;
`;

const DestinationForm = styled.div`
  padding: 2rem 3rem;
  width: 100%;
  height: 100vh;
  box-shadow: 5px 5px 10px -8px lightgrey;

  .destinationInput {
    position: relative;
  }

  .destinationInput svg {
    position: absolute;
    top: 0.7rem;
    right: 1rem;
    font-size: 1.7rem;
    padding: 0.2rem;
    color: #6f6f6f;
  }

  .plus-icon {
    cursor: pointer;
  }
`;

const ButtonContainer = styled.div`
  height: 5rem;
  display: flex;
  align-items: center;
  justify-content: flex-end;
`;
