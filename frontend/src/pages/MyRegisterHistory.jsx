import styled from 'styled-components';
import Header from '../components/Header';
import ListItem from '../components/ListItem';
import useGetData from '../hooks/useGetData';
import { useState, useEffect } from 'react';

export default function MyRegisterHistory() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    useGetData(`https://server.yata.kro.kr/api/v1/yata/myYataRequests`).then(res => {
      setData(res.data.data, setLoading(false));
    });
  }, []);

  return (
    <>
      <Header title="나의 신청 내역" />
      {data.length !== 0 && !loading ? (
        <Container>
          {data.map(el => (
            <ListItem date={el.departureTime} journeyStart={el.strPoint.address} journeyEnd={el.destination.address} />
          ))}
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
