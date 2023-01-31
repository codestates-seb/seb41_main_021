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
              {el.paySuccessYN === false ? <TagFail>실패</TagFail> : <TagSuccess>성공</TagSuccess>}
              <PriceContainer>{el.amount.toLocaleString('ko-KR')}원</PriceContainer>
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
  border-bottom: 1px solid #dddddd;
`;

const DateContainer = styled.div`
  display: flex;
  align-items: center;
  color: ${props => props.theme.colors.gray};
  font-weight: bold;
`;

const PriceContainer = styled.div`
  text-align: end;
  width: 10rem;
  font-size: 1.5rem;
  font-weight: bold;
`;

const TagSuccess = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 0.5rem;
  padding: 0.5rem;
  width: 4.5rem;
  height: 1.8rem;
  background-color: ${props => props.theme.colors.main_blue};
  color: white;
  border-radius: 0.2rem;
  position: relative;
  left: 5rem;
`;

const TagFail = styled(TagSuccess)`
  background-color: ${props => props.theme.colors.light_gray};
`;

export default ChargeList;
