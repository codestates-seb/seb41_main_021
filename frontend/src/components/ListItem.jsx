import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { IoIosArrowForward, IoIosArrowRoundForward } from 'react-icons/io';
import { BsCalendar4, BsPeople } from 'react-icons/bs';
import { BiWon } from 'react-icons/bi';

const ListItem = props => {
  const { date, journeyStart, journeyEnd, price, people, state, yataId, yataStatus } = props;
  const navigate = useNavigate();

  const handleClick = () => {
    yataStatus === 'YATA_NEOTA' ? navigate(`/taeoonda-detail/${yataId}`) : navigate(`/tabnida-detail/${yataId}`);
  };

  const shortWords = (str, length = 10) => {
    let result = '';
    if (str.length > length) {
      result = str.substr(0, length) + '...';
    } else {
      result = str;
    }
    return result;
  };

  // api 응답 어떻게 올지 몰라서 대충 넣어놓음
  return (
    <>
      <Container onClick={handleClick}>
        <TextContainer>
          <DateContainer title="출발일 및 시간">
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
              {shortWords(journeyStart)}
              <IoIosArrowRoundForward />
              {shortWords(journeyEnd)}
            </JourneyText>
            <IoIosArrowForward />
          </JourneyContainer>
          <BottomContainer>
            <PriceContainer title="인당 금액">
              <BiWon /> {price.toLocaleString('ko-KR')}원
            </PriceContainer>
            <PeopleContainer title="현재인원 / 최대인원">
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
  cursor: pointer;

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
