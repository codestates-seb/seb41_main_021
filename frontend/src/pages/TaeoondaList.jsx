import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Button from '../components/common/Button';
import ListItem from '../components/ListItem';
import DestinationInput from '../components/DestinationInput';
import { useNavigate } from 'react-router-dom';

export default function TaeoondaList() {
  const navigate = useNavigate();
  const add = () => {
    navigate('/taeoondaadd');
  };
  return (
    <>
      <Navbar />
      <Container>
        <IputContainer>
          <DestinationInput />
        </IputContainer>
        <ListItem date={'1월 4일 (수) 7:00PM'} journeyStart={'부산'} journeyEnd={'서울'} transit="1"></ListItem>
        <Button onClick={add}>내 여정 등록하기</Button>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const IputContainer = styled.div`
  width: 100%;
  padding: 1rem;
`;
