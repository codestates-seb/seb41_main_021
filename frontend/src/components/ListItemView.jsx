import styled from 'styled-components';
import ListItem from './ListItem';

const ListItemView = props => {
  // api 응답 어떻게 올지 몰라서 대충 넣어놓음
  const { list } = props;
  return (
    <>
      <Container>
        {list.map(el => {
          return (
            <ListItem
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

export default ListItemView;
