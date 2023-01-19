import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Header from '../components/Header';
import ListItemView from '../components/ListItemView';
import CircleButton from '../components/common/CircleButton';
import DestinationInput from '../components/DestinationInput';
import { useNavigate } from 'react-router-dom';
import { MdAdd } from 'react-icons/md';
import useGetData from '../hooks/useGetData';

export default function TaeoondaList() {
  const [open, setOpen] = useState(false);
  const [list, setList] = useState([]);
  const navigate = useNavigate();
  const add = () => {
    setOpen(!open);
    navigate('/taeoonda-add');
  };

  useEffect(() => {
    useGetData('https://server.yata.kro.kr/api/v1/yata?yataStatus=neota').then(res => setList(res.data.data));
  }, []);
  return (
    <>
      <Header title="태웁니다" />
      <Container>
        <DestinationInput />
        <ListItemView list={list} />
        <CircleButton onClick={add} open={open}>
          <MdAdd />
        </CircleButton>
      </Container>
      <Navbar />
    </>
  );
}
const Container = styled.div`
  flex: 1;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
`;
