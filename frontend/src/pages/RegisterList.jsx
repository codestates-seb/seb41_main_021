import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import Navbar from '../components/NavBar';
import ListItemView from '../components/ListItemView';
import Header from '../components/Header';
import useGetData from '../hooks/useGetData';

export default function RegisterList() {
  const navigate = useNavigate();

  const [list, setList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    useGetData(`https://server.yata.kro.kr/api/v1/yata/my`).then(res => {
      setList(res.data.data);
      setLoading(false);
    });
  }, []);

  return (
    <>
      <Header title="요청 내역" />
      {list.length !== 0 && !loading ? (
        <Container>
          <ListItemView
            onClick={() => {
              navigate('/register-checklist');
            }}
            list={list}
          />
        </Container>
      ) : (
        <Container>
          <div className="no-content">요청 내역이 없습니다.</div>
        </Container>
      )}
      <Navbar />
    </>
  );
}

const Container = styled.div`
  flex: 1;
  overflow: scroll;
  display: flex;
  flex-direction: column;
  align-items: center;

  .no-content {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.5rem;
  }
`;
