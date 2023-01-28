import styled from 'styled-components';
import { IoIosArrowRoundForward } from 'react-icons/io';
import { BsCalendar4, BsPeople } from 'react-icons/bs';
import { BiWon } from 'react-icons/bi';
import Button from '../common/Button';
import { useNavigate, useParams } from 'react-router';

const JourneyItem = props => {
  const navigate = useNavigate();
  // const params = useParams();
  // const yataId = params.yataId;
  // api 응답 어떻게 올지 몰라서 대충 넣어놓음
  const { date, journeyStart, journeyEnd, price, people, state, onClick, isPay, yataId } = props;
  return (
    <>
      <Container onClick={onClick}>
        <TextContainer>
          <DateContainer>
            <BsCalendar4 />
            {date}
            {state && <TagContainer state={state}>{state === '대기' ? '도착 전' : '도착'}</TagContainer>}
          </DateContainer>
          <JourneyContainer>
            <JourneyText>
              {journeyStart}
              <IoIosArrowRoundForward />
              {journeyEnd}
            </JourneyText>
            {isPay ? (
              <Button onClick={() => navigate(`/rating-add-driver/${yataId}`)}>리뷰 남기기</Button>
            ) : (
              <Button>결제하기</Button>
            )}
          </JourneyContainer>
          <BottomContainer>
            <PriceContainer>
              <BiWon /> {price}원
            </PriceContainer>
            <PeopleContainer>
              <BsPeople /> {people}명
            </PeopleContainer>
          </BottomContainer>
        </TextContainer>
      </Container>
    </>
  );
};

const Container = styled.div`
  width: 100%;
  height: 9rem;

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
  svg {
    margin-right: 0.5rem;
  }
`;

const DateContainer = styled.div`
  display: flex;
  align-items: center;
  color: ${props => props.theme.colors.gray};
  font-weight: bold;
  margin-bottom: 0.8rem;
`;

const TagContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 0.5rem;
  padding: 0.5rem;
  height: 1.5rem;
  color: white;
  border-radius: 0.2rem;
  font-size: 0.9rem;

  background-color: ${props => (props.state === '대기' ? props.theme.colors.light_red : props.theme.colors.main_blue)};
`;

const JourneyContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 0.7rem;

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

const BottomContainer = styled.div`
  display: flex;
  flex-direction: row;
`;
const PriceContainer = styled.div`
  margin-right: 2rem;
  color: ${props => props.theme.colors.dark_gray};
  svg {
    position: relative;
    top: 0.1rem;
  }
`;
const PeopleContainer = styled.div`
  color: ${props => props.theme.colors.dark_gray};
  svg {
    position: relative;
    top: 0.1rem;
  }
`;

export default JourneyItem;
