import styled from 'styled-components';
export default function ListItem() {
  return (
    <>
      <Container>
        <TextContainer>
          <DateContainer>1월 3일 (화) 7:00PM</DateContainer>
          <JourneyContainer>서울 → 대전</JourneyContainer>
          <TransitContainer>경유 : 0회</TransitContainer>
        </TextContainer>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 7rem;
  border: 1px solid black;
  display: flex;
  align-items: center;
  justify-content: center;
  @media only screen and (min-width: 800px) {
  }
`;

const TextContainer = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  flex-direction: column;
`;

const DateContainer = styled.span`
  flex: 1;
`;

const JourneyContainer = styled.span`
  flex: 1;
`;

const TransitContainer = styled.span`
  flex: 1;
`;
