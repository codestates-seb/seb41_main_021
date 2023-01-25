import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { IoIosArrowForward, IoIosArrowRoundForward } from 'react-icons/io';
import { BsCalendar4, BsPeople } from 'react-icons/bs';
import { BiWon } from 'react-icons/bi';

const ListItem = props => {
  const { date, journeyStart, journeyEnd, transit, price, people, state, yataId, yataStatus } = props;
  const navigate = useNavigate();

  const handleClick = () => {
    yataStatus === 'YATA_NEOTA'
      ? navigate(`/taeoonda-detail/${yataId}`)
      : yataStatus === 'YATA_NATA'
      ? navigate(`/tabnida-detail/${yataId}`)
      : navigate(`/register-checklist`);
  };

  // api 응답 어떻게 올지 몰라서 대충 넣어놓음
  return (
    <>
      <Container onClick={handleClick}>
        <TextContainer>
          <DateContainer>
            <BsCalendar4 />
            {date}
            {state && (
              <TagContainer state={state}>
                {state === '대기' ? '승인 대기' : state === '수락' ? '승인 확정' : '승인 거절'}
              </TagContainer>
            )}
          </DateContainer>
          <JourneyContainer>
            <JourneyText>
              {journeyStart}
              <IoIosArrowRoundForward />
              {journeyEnd}
              <TransitContainer>경유 {transit}회</TransitContainer>
            </JourneyText>
            <IoIosArrowForward />
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

  background-color: ${props =>
    props.state === '대기'
      ? props.theme.colors.gray
      : props.state === '수락'
      ? props.theme.colors.main_blue
      : props.theme.colors.light_red};
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

const TransitContainer = styled.div`
  display: flex;
  align-items: center;
  margin-left: 1rem;
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

export default ListItem;
