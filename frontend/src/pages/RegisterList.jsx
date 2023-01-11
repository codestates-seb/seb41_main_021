import styled from 'styled-components';
import Navbar from '../components/NavBar';
import ListItem from '../components/ListItem';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';

export default function RegisterList() {
  const navigate = useNavigate();
  const login = () => {
    navigate('/login');
  };
  return (
    <>
      <Navbar />
      <Container>
        <Header title="요청 내역" />
        <ListItem date={'1월 3일 (화) 7:00PM'} journeyStart={'서울'} journeyEnd={'부산'} transit="1"></ListItem>
        <ListItem date={'1월 4일 (수) 7:00PM'} journeyStart={'부산'} journeyEnd={'서울'} transit="1"></ListItem>
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
