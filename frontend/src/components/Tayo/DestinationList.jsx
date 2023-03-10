import styled from 'styled-components';
import DestinationItem from './DestinationItem';

export default function DestinationList(props) {
  const { places } = props;

  return (
    // address_name //place_name
    <>
      <Container>
        {places.map(el => {
          return (
            <DestinationItem
              key={el.id}
              addressName={el.address_name}
              placeName={el.place_name}
              longitude={el.x}
              latitude={el.y}
            />
          );
        })}
      </Container>
    </>
  );
}
const Container = styled.div`
  width: 100%;

  & > div:first-child {
    margin-top: 2.5rem;
    border-top: 1px solid #f3f3f3;
  }
`;
