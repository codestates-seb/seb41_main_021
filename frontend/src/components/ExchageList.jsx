import styled from 'styled-components';

const ListItem = props => {
  const { date, price, onClick } = props;
  return (
    <>
      <Container onClick={onClick}>
        <TextContainer>
          <DateContainer>{date}</DateContainer>
          <PriceContainer>{price}</PriceContainer>
        </TextContainer>
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

export default ListItem;
