import styled from 'styled-components';
import { dateFormat } from './common/DateFormat';

const ChargeList = props => {
  const { onClick, list } = props;
  return (
    <>
      <Container onClick={onClick}>
        {list.map(el => {
          return (
            <TextContainer>
              <DateContainer>{dateFormat(el.createdAt)}</DateContainer>
              <PriceContainer>{el.amount}원</PriceContainer>
            </TextContainer>
          );
        })}
      </Container>
    </>
  );
};

const Container = styled.div`
  width: 100%;
  height: 6rem;

  border-bottom: 1px solid #f6f6f6;
  @media only screen and (min-width: 800px) {
  }
`;

const TextContainer = styled.div`
  width: 100%;
  height: 100%;
  padding: 2rem;
  display: flex;
  justify-content: space-between;
  flex-direction: row;
`;

const DateContainer = styled.div`
  display: flex;
  align-items: center;
  color: ${props => props.theme.colors.gray};
  font-weight: bold;
`;

const PriceContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 1.5rem;
  font-weight: bold;
`;

export default ChargeList;
