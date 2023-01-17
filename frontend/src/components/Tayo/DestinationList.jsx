import styled from 'styled-components';
import DestinationItem from './DestinationItem';

export default function DestinationList(props) {
  const { Places } = props;

  return (
    // address_name //place_name
    <>
      <Container>
        {Places.map(el => {
          return <DestinationItem addressName={el.address_name} placeName={el.place_name} />;
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