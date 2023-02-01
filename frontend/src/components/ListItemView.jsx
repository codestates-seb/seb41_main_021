import styled from 'styled-components';
import ListItem from './ListItem';
import { dateFormat } from './common/DateFormat';
import { useState, useEffect } from 'react';

const ListItemView = props => {
  const { list, children } = props;

  return (
    <>
      <Container>
        {list.map(el => {
          return (
            <ListItem
              key={el.yataId}
              yataId={el.yataId}
              date={dateFormat(el.departureTime)}
              journeyStart={el.strPoint.address}
              journeyEnd={el.destination.address}
              price={el.amount}
              people={`1/${el.maxPeople}`}
              yataStatus={el.yataStatus}
              postStatus={el.postStatus}
            />
          );
        })}
        {/* {loading && <Loading>데이터를 불러오는 중입니다..</Loading>} */}
        {children}
      </Container>
    </>
  );
};

const Container = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: scroll;
`;

const Loading = styled.div`
  width: 100%;
  margin: 1rem;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export default ListItemView;
