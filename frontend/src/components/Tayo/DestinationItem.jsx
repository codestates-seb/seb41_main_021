import styled, { css } from 'styled-components';
import Button from '../common/Button';
import { useNavigate } from 'react-router-dom';

export default function DestinationItem(props) {
  const navigate = useNavigate();
  // address_name // id // x // y //place_name
  const { addressName, placeName, x, y } = props;

  return (
    <>
      <Container
        onClick={() => navigate(`/destination-detail?address=${addressName}&place=${placeName}&x=${x}&y=${y}`)}>
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
