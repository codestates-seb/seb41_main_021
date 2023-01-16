import styled, { css } from 'styled-components';
import Button from '../common/Button';

export default function DestinationItem(props) {
  // address_name // id // x // y //place_name
  const { addressName, placeName } = props;
  console.log(addressName, placeName);

  return (
    <>
      <Container>
        <div className="place-container">
          <h2 className="place-name">{placeName}</h2>
          <div className="place-address">{addressName}</div>
        </div>
        <Button>선택하기</Button>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  padding: 1.5rem 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #f3f3f3;

  .place-address {
    font-size: 0.8rem;
  }
`;
