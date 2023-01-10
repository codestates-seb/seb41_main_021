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
        <ListItem></ListItem>
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
