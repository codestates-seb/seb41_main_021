import { useState } from 'react';
import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Header from '../components/Header';
import CircleButton from '../components/common/CircleButton';
import ListItem from '../components/ListItem';
import DestinationInput from '../components/DestinationInput';
import { useNavigate } from 'react-router-dom';
import { MdAdd } from 'react-icons/md';

export default function TaeoondaList() {
  const [open, setOpen] = useState(false);
  const navigate = useNavigate();
  const add = () => {
    setOpen(!open);
    navigate('/taeoondaadd');
  };
  return (
    <>
      <Header title="TaeoondaList" />
      <Navbar />
      <Container>
        <DestinationInput />
        <ListItem date={'1월 4일 (수) 7:00PM'} journeyStart={'부산'} journeyEnd={'서울'} transit="1"></ListItem>
      </Container>
      <CircleButton onClick={add} open={open}>
        <MdAdd />
      </CircleButton>
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
