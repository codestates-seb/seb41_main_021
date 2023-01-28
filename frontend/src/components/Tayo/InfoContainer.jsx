import styled from 'styled-components';
import { IoIosArrowRoundForward, IoIosArrowForward } from 'react-icons/io';
import { BsCalendar4, BsPeople } from 'react-icons/bs';
import { BiWon, BiCommentDetail } from 'react-icons/bi';
import { AiOutlineCar } from 'react-icons/ai';
import { dateFormat } from '../common/DateFormat';

export default function InfoContainer(props) {
  const { data, ableTag, disableTag } = props;

  return (
    <ContentContainer>
      <JourneyContainer>
        <h2>{data.strPoint.address}</h2>
        {/* <IoIosArrowRoundForward />
        <h2>경유지</h2> */}
        <IoIosArrowRoundForward />
        <h2>{data.destination.address}</h2>
        <TagContainer state={data.postStatus}>{data.postStatus === 'POST_OPEN' ? ableTag : disableTag}</TagContainer>
      </JourneyContainer>
      <DateContainer title="출발일 및 시간">
        <BsCalendar4 />
        <p className="date-txt"> {dateFormat(data.departureTime)}</p>
      </DateContainer>
      <PriceContainer title="인당 금액">
        <BiWon />
        <p className="price-txt">{data.amount.toLocaleString('ko-KR')}원</p>
      </PriceContainer>
      <AmountContainer>
        <BsPeople />
        <p className="amount-txt">{data.maxPeople} 명</p>
      </AmountContainer>
      {data.yataStatus === 'YATA_NEOTA' ? (
        <CarContainer title="차종">
          <AiOutlineCar />
          <p className="car-txt">{data.carModel}</p>
        </CarContainer>
      ) : (
        <></>
      )}
      <MemoContainer title="기타 사항">
        <BiCommentDetail />
        <p className="memo-txt">{data.specifics}</p>
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

  background-color: ${props =>
    props.state === 'POST_OPEN' ? props.theme.colors.main_blue : props.theme.colors.light_gray};
`;

const DateContainer = styled.div``;

const PriceContainer = styled.div``;

const AmountContainer = styled.div``;

const CarContainer = styled.div``;

const MemoContainer = styled.div``;
