import styled from 'styled-components';
const ListItem = props => {
  // api 응답 어떻게 올지 몰라서 대충 넣어놓음
  const { date, journeyStart, journeyEnd, transit } = props;
  return (
    <>
      <Container>
        <TextContainer>
          <DateContainer>{date}</DateContainer>
          <JourneyContainer>
            {journeyStart} → {journeyEnd}
          </JourneyContainer>
          <TransitContainer>경유 : {transit}회</TransitContainer>
        </TextContainer>
      </Container>
    </>
  );
};

const Container = styled.div`
  width: 100%;
  height: 10rem;
  border-bottom: 1px solid ${props => props.theme.colors.gray};
  @media only screen and (min-width: 800px) {
  }
`;

const TextContainer = styled.div`
  width: 100%;
  height: 100%;
  padding: 0.5rem;
  display: flex;
  justify-content: center;
  flex-direction: column;
`;

const DateContainer = styled.div`
  display: flex;
  align-items: center;
  flex: 1;
`;

const JourneyContainer = styled.div`
  display: flex;
  align-items: center;
  flex: 1;
  font-size: 1.5rem;
  font-weight: bold;
`;

const TransitContainer = styled.div`
  display: flex;
  align-items: center;
  flex: 1;
`;

export default ListItem;
