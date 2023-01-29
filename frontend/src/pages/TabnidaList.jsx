import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Navbar from '../components/NavBar';
import Header from '../components/Header';
import CircleButton from '../components/common/CircleButton';
import DestinationInput from '../components/DestinationInput';
import { useNavigate } from 'react-router-dom';
import { MdAdd } from 'react-icons/md';
import ListItemView from '../components/ListItemView';
import { useGetData } from '../hooks/useGetData';
import { useDispatch } from 'react-redux';
import { clearAll } from '../redux/slice/DestinationSlice';

export default function TabnidaList() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [list, setList] = useState([]);
  const [target, setTarget] = useState(null);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(true);
  const [hasNext, setHasNext] = useState(false);

  const fetch = () => {
    useGetData(
      `https://server.yata.kro.kr/api/v1/yata/search/location?distance=1&yataStatus=YATA_NATA&page=${page}&size=20`,
    ).then(res => {
      setList(prev => prev.concat(res.data.data));
      setPage(prev => prev + 1);
      setHasNext(res.data.sliceInfo.hasNext);
      setLoading(false);
    });
  };
  const add = () => {
    setOpen(!open);
    navigate('/tabnida-add');
  };

  const temp = () => {
    console.log(page);
    console.log(list);
    console.log(hasNext);
  };

  useEffect(() => {
    fetch();
    dispatch(clearAll());
  }, []);

  useEffect(() => {
    let observer;
    if (target) {
      const onIntersect = async ([entry], observer) => {
        if (entry.isIntersecting) {
          observer.unobserve(entry.target);
          fetch();
          console.log('관측');
          setTimeout(() => {
            observer.observe(entry.target);
          }, 1000);
        }
      };
      observer = new IntersectionObserver(onIntersect, { threshold: 1 });
      observer.observe(target);
    }
    return () => observer && observer.disconnect();
  }, [target]);

  return (
    <>
      {loading || (
        <>
          <Header title="탑니다" />
          <Container onClick={temp}>
            <DestinationInput />
            <ListItemView list={list}>
              <div ref={setTarget}></div>
            </ListItemView>
            <CircleButton onClick={add} open={open}>
              <MdAdd />
            </CircleButton>
          </Container>
          <Navbar />
        </>
      )}
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
