import styled from 'styled-components';
import Header from '../components/Header';
import ListItemView from '../components/ListItemView';
import { useGetData } from '../hooks/useGetData';
import { useState, useEffect } from 'react';

export default function MyRegisterHistory() {
  const [list, setList] = useState([]);

  useEffect(() => {
    useGetData('https://server.yata.kro.kr/api/v1/yata/requests/myYataRequests').then(res => {
      setList(res.data.data);
    });
  }, []);

  return (
    <>
      <Header title="나의 신청/초대 내역" />
      {list.length !== 0 ? (
        <Container>
          <ListItemView list={list} />
        </Container>
      ) : (
        <Container>
          <div className="no-content">신청한 내역이 없습니다.</div>
        </Container>
      )}
    </>
  );
}

const Container = styled.div`
  flex: 1;
  overflow: scroll;

  .no-content {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.5rem;
  }
`;
