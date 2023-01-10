import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Button from '../components/common/Button';
import ListItem from '../components/ListItem';
import DestinationInput from '../components/DestinationInput';
import { useNavigate } from 'react-router-dom';

export default function TabnidaList() {
  const navigate = useNavigate();
  const add = () => {
    navigate('/tabnidaadd');
  };
  return (
    <>
      <Navbar />
      <Container>
        <DestinationInput />
        <ListItem
          date={'1월 3일 (화) 7:00PM'}
          journeyStart={'성수 SPOT 01 외'}
          journeyEnd={'용산 HUB'}
          transit="1"></ListItem>
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
