import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Header from '../components/Header';
import ListItemView from '../components/ListItemView';
import CircleButton from '../components/common/CircleButton';
import DestinationInput from '../components/DestinationInput';
import { useNavigate } from 'react-router-dom';
import { MdAdd } from 'react-icons/md';
import { useGetData } from '../hooks/useGetData';
import { useDispatch } from 'react-redux';
import { clearAll } from '../redux/slice/DestinationSlice';
import { FcCheckmark } from 'react-icons/fc';

export default function TaeoondaList() {
  const dispatch = useDispatch();
  const [open, setOpen] = useState(false);
  const [list, setList] = useState([]);
  const navigate = useNavigate();
  const add = () => {
    setOpen(!open);
    navigate('/taeoonda-add');
  };

  useEffect(() => {
    useGetData('/api/v1/yata?yataStatus=neota&page=0&size=100').then(res => setList(res.data.data));
    dispatch(clearAll());
  }, []);

  return (
    <>
      <Header title="태웁니다" />
      <Container>
        <HelpComent>
          <FcCheckmark />
          <div>운전자</div>가 '태웁니다' 게시물을 작성할 수 있는 페이지입니다.
        </HelpComent>
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

const HelpComent = styled.div`
  width: 95%;
  background-color: #e3f6ff;
  color: #37352f;

  display: flex;
  align-items: center;
  justify-content: center;

  padding: 1rem;
  border-radius: 0.5rem;
  margin-top: 1rem;

  svg {
    font-size: 1.4rem;
    margin-right: 0.3rem;
    margin-bottom: 0.3rem;
  }

  div {
    font-weight: bold;
  }
`;
