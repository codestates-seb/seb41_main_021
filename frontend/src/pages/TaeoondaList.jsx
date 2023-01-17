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
    navigate('/taeoonda-add');
  };
  return (
    <>
      <Container>
        <Header title="태웁니다" />
        <DestinationInput />
        <ListItem
          date={'1월 4일 (수) 7:00PM'}
          journeyStart={'부산'}
          journeyEnd={'서울'}
          transit="1"
          price="10000"
          people="1/4"></ListItem>
        <CircleButton onClick={add} open={open}>
          <MdAdd />
        </CircleButton>
      </Container>
      <Navbar />
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
`;
