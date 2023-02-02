import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Header from '../components/Header';
import CircleButton from '../components/common/CircleButton';
import { useNavigate } from 'react-router-dom';
import { MdAdd } from 'react-icons/md';
import ListItemView from '../components/ListItemView';
import { useGetData } from '../hooks/useGetData';
import { useDispatch } from 'react-redux';
import { clearAll } from '../redux/slice/DestinationSlice';
import { FcCheckmark } from 'react-icons/fc';

export default function TabnidaList() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [list, setList] = useState([]);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(true);

  const fetch = url => {
    useGetData(url).then(res => {
      setList(prev => prev.concat(res.data.data));
      setLoading(false);
      setPage(prev => prev + 1);
    });
  };
  const add = () => {
    setOpen(!open);
    navigate('/tabnida-add');
  };

  useEffect(() => {
    fetch(`/api/v1/yata/search/location?distance=1&yataStatus=YATA_NATA&page=${page}&size=100`);

    dispatch(clearAll());
  }, []);

  return (
    <>
      <Header title="탑니다" />
      <Container>
        <HelpComent>
          <FcCheckmark />
          <div>탑승자</div>가 '탑니다' 게시물을 작성할 수 있는 페이지입니다.
        </HelpComent>
        <ListItemView list={list}></ListItemView>
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
  background-color: #f9ebc8;
  color: #37352f;

  display: flex;
  align-items: center;
  justify-content: center;

  padding: 1rem;
  border-radius: 0.5rem;
  margin-top: 1rem;
  margin-bottom: 1rem;

  svg {
    font-size: 1.4rem;
    margin-right: 0.3rem;
    margin-bottom: 0.3rem;
  }

  div {
    font-weight: bold;
  }
`;
