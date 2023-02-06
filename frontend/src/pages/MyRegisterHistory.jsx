import styled from 'styled-components';
import Header from '../components/Header';
import ListItemView from '../components/ListItemView';
import { useGetData } from '../hooks/useGetData';
import { useState, useEffect } from 'react';
// TiDelete

export default function MyRegisterHistory() {
  const [list, setList] = useState([]);
  const [update, setUpdate] = useState(true);

  useEffect(() => {
    if (update) {
      useGetData('/api/v1/yata/requests/myYataRequests').then(res => {
        setList(res.data.data);
        setUpdate(false);
      });
    }
  }, [update]);

  return (
    <>
      <Header title="나의 신청 내역" />
      {list.length !== 0 ? (
        <Container>
          <ListItemView list={list} isMyRegisterHistory={true} setUpdate={setUpdate} />
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
