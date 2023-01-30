import styled from 'styled-components';
import Header from '../components/Header';
import ChargeList from '../components/ChargeList';
import useChargeList from '../hooks/useChargeList';
import { useState, useEffect } from 'react';

export default function PointHistory() {
  const [list, setList] = useState([]);
  const [data, setData] = useState([]);

  useEffect(() => {
    useChargeList('https://server.yata.kro.kr/api/v1/payHistory').then(res => {
      setList(res.data.data);
      setData(res.data.data);
      console.log(res.data.data);
    });
  }, []);
  return (
    <>
      <Header title="충전 내역" />
      {data.length !== 0 ? (
        <Container>
          <ChargeList list={list} />
        </Container>
      ) : (
        <Container>
          <div className="no-content">충전한 내역이 없습니다.</div>
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
