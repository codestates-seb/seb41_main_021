import styled from 'styled-components';
import Button from '../components/common/Button';
import NavBar from '../components/NavBar';
import Header from '../components/Header';
import { IoIosArrowRoundForward, IoIosArrowForward } from 'react-icons/io';
import { BsCalendar4, BsPeople } from 'react-icons/bs';
import { BiWon, BiCommentDetail } from 'react-icons/bi';
import { VscAccount } from 'react-icons/vsc';

export default function TabnidaDetail() {
  return (
    <>
      <NavBar />
      <Container>
        <Header title={'탑니다'}></Header>
        <ProfileContainer>
          <ProfileInfoContainer>
            <h2>작성자 정보</h2>
            <ProfileInfo>
              <VscAccount />
              <ProfileText>
                <div className="username">sepunjix0911</div>
                <div>기름통 레벨 70L</div>
              </ProfileText>
            </ProfileInfo>
          </ProfileInfoContainer>
          <IoIosArrowForward />
        </ProfileContainer>
        <InfoContainer>
          <JourneyContainer>
            <h2>출발지</h2>
            <IoIosArrowRoundForward />
            <h2>경유지</h2>
            <IoIosArrowRoundForward />
            <h2>도착지</h2>
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
        </InfoContainer>

        <InviteButton>초대하기</InviteButton>
      </Container>
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const ProfileContainer = styled.div`
  width: 95%;
  margin-top: 1rem;
  padding: 1rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-radius: 0.3rem;
  background-color: #f6f6f6;
  color: #202020;

  svg {
    font-size: 1.2rem;
    margin-right: 0.5rem;
  }

  .username {
    font-weight: bold;
    margin-bottom: 0.3rem;
  }
`;

const ProfileInfoContainer = styled.div`
  display: flex;
  flex-direction: column;

  svg {
    font-size: 2rem;
  }
`;

const ProfileInfo = styled.div`
  margin: 1rem 0;
  display: flex;
  align-items: center;
`;

const ProfileText = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 0.3rem;
`;

const InfoContainer = styled.div`
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

  div {
    display: flex;
    align-items: center;
    margin: 1.2rem 0;
  }
`;

const JourneyContainer = styled.div`
  padding: 0.5rem 0;
`;

const DateContainer = styled.div``;

const PriceContainer = styled.div``;

const AmountContainer = styled.div``;

const MemoContainer = styled.div``;

const InviteButton = styled(Button)`
  width: 40%;
  margin-top: 1rem;
`;
// const DateBox = styled.div`
//   width: 100%;
//   height: auto;
//   background-color: ${props => props.theme.colors.dark_blue};
//   padding: 1rem 1rem 1rem 2rem;
//   color: #fff;
//   display: flex;
//   flex-direction: column;
//   box-shadow: 0px 4px 4px -4px #3f5179;

//   div {
//     font-size: 1.4rem;
//     line-height: 2.2rem;
//   }
// `;
// const DestinationBox = styled.div`
//   width: 100%;
//   height: 70%;
//   padding: 1rem;
// `;
// const Departure = styled.div`
//   width: 100%;
//   height: 50%;
//   padding: 1rem;
//   display: flex;
//   box-shadow: 0px 1px 1px -1px #a4a3a4;
// `;
// const Destination = styled.div`
//   width: 100%;
//   height: 50%;
//   padding: 1rem;
//   display: flex;
// `;
// const Title = styled.div`
//   width: 30%;
//   height: 100%;
//   color: ${props => props.theme.colors.darker_blue};
//   font-weight: bold;
//   font-size: 1.5rem;
// `;
// const Address = styled.div`
//   width: 100%;
//   height: 100%;
//   font-size: 1.5rem;
// `;
// const BottomContainer = styled.div`
//   width: 100%;
//   height: 40%;
//   margin: 1rem;
//   background-color: #fafbfc;
//   box-shadow: 0px 4px 4px -4px #a4a3a4;
//   border-radius: 1rem;
//   padding: 2rem;
// `;
// const StopOverPassenger = styled.div`
//   width: 100%;
//   height: 30%;
//   display: flex;
//   justify-content: space-around;
// `;
// const StopOver = styled.div`
//   width: 30%;
//   height: 100%;
//   border-radius: 1rem;
// `;
// const Heading = styled.div`
//   width: 100%;
//   height: 50%;
//   box-shadow: 0px 3px 3px -3px #a4a3a4;
//   display: flex;
//   align-items: center;
//   justify-content: center;
//   font-size: 1.5rem;
//   color: ${props => props.theme.colors.darker_blue};
//   font-weight: bold;
// `;
// const Body = styled.div`
//   width: 100%;
//   height: 50%;
//   display: flex;
//   align-items: center;
//   justify-content: center;
//   font-size: 1.3rem;
//   color: black;
//   /* font-weight: bold; */
// `;
// const SpecialNote = styled.div`
//   width: 100%;
//   height: 60%;
//   margin-top: 2rem;
// `;
// const SpecialHeading = styled(Heading)`
//   height: 25%;
//   color: ${props => props.theme.colors.main_blue};
// `;
// const SpecialBody = styled(Body)`
//   height: 80%;
//   line-height: 2rem;
// `;

// const JourneyText = styled.span`
//   display: flex;
//   align-items: center;
//   font-size: 1.5rem;
//   font-weight: bold;
//   svg {
//     color: ${props => props.theme.colors.gray};
//     font-size: 2rem;
//     margin: 0 0.5rem;
//   }
// `;
