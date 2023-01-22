import styled from 'styled-components';
import { IoIosArrowRoundForward, IoIosArrowForward } from 'react-icons/io';
import { BsCalendar4, BsPeople } from 'react-icons/bs';
import { BiWon, BiCommentDetail } from 'react-icons/bi';

export default function InfoContainer(props) {
  const { state } = props;

  return (
    <ContentContainer>
      <JourneyContainer>
        <h2>출발지</h2>
        <IoIosArrowRoundForward />
        <h2>경유지</h2>
        <IoIosArrowRoundForward />
        <h2>도착지</h2>
        {state && <TagContainer state={state}>{state === '가능' ? '신청 가능' : '신청 마감'}</TagContainer>}
      </JourneyContainer>
      <DateContainer>
        <BsCalendar4 />
        <p className="date-txt">2월 11일 토요일, 오후 5:00</p>
      </DateContainer>
      <PriceContainer>
        <BiWon />
        <p className="price-txt">10,000 원</p>
      </PriceContainer>
      <AmountContainer>
        <BsPeople />
        <p className="amount-txt">2 명</p>
      </AmountContainer>
      <MemoContainer>
        <BiCommentDetail />
        <p className="memo-txt">짐이 많아요 ㅠ ㅠ</p>
      </MemoContainer>
    </ContentContainer>
  );
}

const ContentContainer = styled.div`
  width: 90%;
  margin: 1rem 0;

  svg {
    font-size: 1.2rem;
  }

  h2 {
    font-size: 1.4rem;
  }

  p {
    font-size: 1.1rem;
    margin-left: 0.5rem;
  }

  & > div {
    display: flex;
    align-items: center;
    margin: 1.2rem 0;
  }
`;

const JourneyContainer = styled.div``;

const TagContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 0.5rem;
  padding: 0.5rem;
  height: 1.8rem;
  color: white;
  border-radius: 0.2rem;

  background-color: ${props => (props.state === '가능' ? props.theme.colors.main_blue : props.theme.colors.light_gray)};
`;

const DateContainer = styled.div``;

const PriceContainer = styled.div``;

const AmountContainer = styled.div``;

const MemoContainer = styled.div``;
