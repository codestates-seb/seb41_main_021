import { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import Navbar from '../components/NavBar';
import ListItemView from '../components/ListItemView';
import Header from '../components/Header';
import useGetData from '../hooks/useGetData';

export default function RegisterList() {
  const navigate = useNavigate();
  const params = useParams();
  const yataId = params.yataId;
  const [list, setList] = useState([]);

  useEffect(() => {
    useGetData(`https://server.yata.kro.kr/api/v1/yata/apply/yataRequests`).then(
      res => console.log(res),
      // setList(res.data.data)
    );
  }, []);

  return (
    <>
      <Header title="요청 내역" />
      <Container>
        {list.map(el => {
          return (
            <ListItemView
              onClick={() => {
                navigate('/register-checklist');
              }}
              key={el.yataId}
              yataId={el.yataId}
              date={el.departureTime}
              journeyStart={el.strPoint.address}
              journeyEnd={el.destination.address}
              transit={'1'}
              price={el.amount}
              people={`1/${el.maxPeople}`}
              yataStatus={el.yataStatus}
            />
          );
        })}
      </Container>
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
`;
