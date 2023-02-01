import styled from 'styled-components';
import { IoIosArrowRoundForward } from 'react-icons/io';
import { BsCalendar4, BsPeople } from 'react-icons/bs';
import { BiWon } from 'react-icons/bi';
import Button from '../common/Button';
import { useNavigate, useParams } from 'react-router';
import usePostData from '../../hooks/usePostData';
import { useState } from 'react';
import Modal from '../common/Modal';

const JourneyItem = props => {
  const [show, setShow] = useState(false);

  const navigate = useNavigate();

  const shortWords = (str, length = 8) => {
    let result = '';
    if (str.length > length) {
      result = str.substr(0, length) + '...';
    } else {
      result = str;
    }
    return result;
  };

  const reviewHandler = () => {
    navigate(`/rating-add-driver/${yataId}`);
    console.log(reviewReceived);
  };

  const payHandler = () => {
    const data = {};
    usePostData(`/api/v1/yata/${yataId}/${yataMemberId}/payPoint`, data).then(res => {
      window.location.reload();
    });
  };


  const { date, journeyStart, journeyEnd, price, people, state, onClick, isPay, yataId, yataMemberId, reviewReceived } =
    props;
    
  return (
    <>
      <Container onClick={onClick}>
        <TextContainer>
          <DateContainer>
            <BsCalendar4 />
            {date}
            <TagContainer isPay={isPay}>{isPay ? '도착' : '도착 전'}</TagContainer>
          </DateContainer>
          <JourneyContainer>
            <JourneyText>
              {shortWords(journeyStart)}
              <IoIosArrowRoundForward />
              {shortWords(journeyEnd)}
            </JourneyText>
            {!isPay ? (
              <>
                <Button
                  onClick={() => {
                    setShow(true);
                  }}>
                  결제하기
                </Button>
                <Modal show={show} onClose={() => setShow(false)} title={'결제하시겠습니까?'} event={payHandler}>
                  결제를 하면 <Strong>'도착 전'</Strong>에서 <Strong>'도착'</Strong> 상태로 변경되며,
                  <br /> 포인트가 자동으로 차감됩니다.
                </Modal>
              </>
            ) : reviewReceived ? (
              <Button onClick={reviewHandler}>리뷰 남기기</Button>
            ) : (
              <CompleteBtn>리뷰 완료</CompleteBtn>
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

  background-color: ${props => (props.isPay ? props.theme.colors.main_blue : props.theme.colors.light_red)};
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
`;
const PeopleContainer = styled.div`
  color: ${props => props.theme.colors.dark_gray};
`;

const Strong = styled.span`
  font-size: 1.2rem;
  font-weight: bold;
`;
const CompleteBtn = styled(Button)`
  background-color: ${props => props.theme.colors.gray};

  :hover {
    background-color: ${props => props.theme.colors.gray};
  }
  :active {
    background-color: ${props => props.theme.colors.gray};
  }
  cursor: initial;
`;

export default JourneyItem;
