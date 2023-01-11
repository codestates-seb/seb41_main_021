import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Button from '../components/common/Button';
import ListItem from '../components/ListItem';
import DestinationInput from '../components/DestinationInput';
import { useNavigate } from 'react-router-dom';

export default function RegisterList() {
  const navigate = useNavigate();
  const login = () => {
    navigate('/login');
  };
  return (
    <>
      <Navbar />
      <Container>
        <ListItem date={'1월 3일 (화) 7:00PM'} journeyStart={'서울'} journeyEnd={'부산'} transit="1"></ListItem>
        <ListItem date={'1월 4일 (수) 7:00PM'} journeyStart={'부산'} journeyEnd={'서울'} transit="1"></ListItem>
        <Button onClick={login}>임시용 로그인 페이지로 이동</Button>
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
  //임시
  button {
    margin: 3rem;
  }
`;
