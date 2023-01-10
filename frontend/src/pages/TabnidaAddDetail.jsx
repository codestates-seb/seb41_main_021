import styled from 'styled-components';
import KakaoMap from '../components/KakaoMap';
import Input from '../components/common/Input';
import Button from '../components/common/Button';

import { useNavigate } from 'react-router-dom';

export default function TabnidaAddDetail() {
  const navigate = useNavigate();
  const next = () => {
    navigate('/');
  };
  return (
    <>
      <Container>
        <KakaoMap></KakaoMap>
        <DestinationForm>
          <Input label="출발 시간" />
          <Input label="탑승 인원" />
          <Input label="특이사항" />
          <Button onClick={next}>등록하기</Button>
        </DestinationForm>
      </Container>
    </>
  );
}
const Container = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
`;

const DestinationForm = styled.div`
  background-color: white;
  width: 100%;
  height: 10rem;
`;
