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
    useGetData(`https://server.yata.kro.kr/api/v1/yata/apply/yataRequests`).then(res => setList(res.data.data));
  }, []);

  return (
    <>
      <Header title="요청 내역" />
      <Container>
        {list.map(e => {
          return (
            <ListItemView
              onClick={() => {
                navigate('/register-checklist');
              }}
              key={'123'}
              yataId={'123'}
              date={'2023-01-26T14:31:37'}
              journeyStart={'서울'}
              journeyEnd={'부산'}
              transit={'1'}
              price={'12,000'}
              people={`1/4`}
              yataStatus={'INVITE'}
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
