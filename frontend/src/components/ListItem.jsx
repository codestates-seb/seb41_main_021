import styled from 'styled-components';
import { IoIosArrowForward, IoIosArrowRoundForward } from 'react-icons/io';

const ListItem = props => {
  // api 응답 어떻게 올지 몰라서 대충 넣어놓음
  const { date, journeyStart, journeyEnd, transit, onClick } = props;
  return (
    <>
      <Container onClick={onClick}>
        <TextContainer>
          <DateContainer>{date}</DateContainer>
          <JourneyContainer>
            <JourneyText>
              {journeyStart}
              <IoIosArrowRoundForward />
              {journeyEnd}
            </JourneyText>
            <IoIosArrowForward />
          </JourneyContainer>
          <TransitContainer>경유 {transit}회</TransitContainer>
        </TextContainer>
      </Container>
    </>
  );
};

const Container = styled.div`
  width: 100%;
  height: 8rem;

  border-bottom: 1px solid #f6f6f6;
  @media only screen and (min-width: 800px) {
  }
`;

const TextContainer = styled.div`
  width: 100%;
  height: 100%;
  padding: 1rem;
  display: flex;
  justify-content: center;
  flex-direction: column;
`;

const DateContainer = styled.div`
  display: flex;
  align-items: center;
  color: ${props => props.theme.colors.gray};
  font-weight: bold;
  margin-bottom: 0.5rem;
`;

const JourneyContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1rem;

  svg {
    color: ${props => props.theme.colors.gray};
    font-size: 1.5rem;
  }
`;

const JourneyText = styled.span`
  display: flex;
  align-items: center;
  font-size: 1.5rem;
  font-weight: bold;
  svg {
    color: ${props => props.theme.colors.gray};
    font-size: 2rem;
    margin: 0 0.5rem;
  }
`;

const TransitContainer = styled.div`
  display: flex;
  align-items: center;
`;

export default ListItem;
