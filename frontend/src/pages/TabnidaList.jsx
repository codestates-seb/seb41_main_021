import { useState } from 'react';
import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Header from '../components/Header';
import CircleButton from '../components/common/CircleButton';
import ListItem from '../components/ListItem';
import DestinationInput from '../components/DestinationInput';
import { useNavigate } from 'react-router-dom';
import { MdAdd } from 'react-icons/md';

export default function TabnidaList() {
  const [open, setOpen] = useState(false);
  const navigate = useNavigate();
  const add = () => {
    setOpen(!open);
    navigate('/tabnidaadd');
  };

  return (
    <>
      <Header title="TabnidaList" />
      <Navbar />
      <Container>
        <DestinationInput />
        <ListItem
          date={'1월 3일 (화) 7:00PM'}
          journeyStart={'성수 SPOT 01 외'}
          journeyEnd={'용산 HUB'}
          transit="1"></ListItem>
        <ListItem
          date={'1월 3일 (화) 7:00PM'}
          journeyStart={'성수 SPOT 01 외'}
          journeyEnd={'용산 HUB'}
          transit="1"></ListItem>
        <CircleButton onClick={add} open={open}>
          <MdAdd />
        </CircleButton>
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
