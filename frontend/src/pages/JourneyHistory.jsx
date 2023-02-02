import styled from 'styled-components';
import Header from '../components/Header';
import ListItemView from '../components/ListItemView';
import { useState, useEffect } from 'react';
import { useGetData } from '../hooks/useGetData';

export default function JourneyHistory() {
  const [list, setList] = useState([]);
  useEffect(() => {
    useGetData('/api/v1/yata/myYatas').then(res => {
      setList(res.data.data);
      console.log(res);
    });
  }, []);

  return (
    <>
      <Header title="내가 작성한 글" />
      <Container>
        <ListItemView list={list} isJourneyHistory={true} />
      </Container>
    </>
  );
}

const Container = styled.div`
  flex: 1;
  overflow: scroll;
`;
