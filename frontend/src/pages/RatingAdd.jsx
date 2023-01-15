import styled from 'styled-components';
import NavBar from '../components/NavBar';
import Header from '../components/Header';
import { VscAccount } from 'react-icons/vsc';
import Button from '../components/common/Button';
import RatingList from '../components/rating/RatingList';

export default function RatingAdd(props) {
  const goodRatingList = [
    {
      id: 0,
      text: '운전을 잘해요',
    },
    {
      id: 1,
      text: '친절하고 매너가 좋아요',
    },
    {
      id: 2,
      text: '깔끔해요',
    },
    {
      id: 3,
      text: '시간약속을 잘 지켜요',
    },
    {
      id: 4,
      text: '응답이 빨라요',
    },
  ];

  const badRatingList = [
    {
      id: 0,
      text: '운전이 미숙해요',
    },
    {
      id: 1,
      text: '불친절해요',
    },
    {
      id: 2,
      text: '청결하지 않아요',
    },
    {
      id: 3,
      text: '약속을 안 지켜요',
    },
    {
      id: 4,
      text: '응답이 느려요',
    },
  ];

  return (
    <>
      <NavBar />
      <Container>
        <Header title={'매너 평가'}></Header>
        <ProfileCotainer>
          <Profile>
            <VscAccount />
            <Info>
              <div className="name">닉네임</div>
              <div className="date">2023.01.11 (수) </div>
            </Info>
          </Profile>
        </ProfileCotainer>
        <RatingList title={'👍 좋았던 점을 선택해 주세요 !'} Items={goodRatingList} />
        <RatingList title={' 👎 아쉬웠던 점을 선택해 주세요 !'} Items={badRatingList} />

        <FinishBtn>완료</FinishBtn>
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
const ProfileCotainer = styled.div`
  width: 90%;
  height: 10%;
  padding: 1rem;
  display: flex;
  align-items: center;
`;
const Profile = styled.div`
  width: 50%;
  height: 100%;
  padding: 0.5rem;
  display: flex;
  align-items: center;

  svg {
    font-size: 4rem;
    color: #26264c;
  }
`;
const Info = styled.div`
  width: 60%;
  height: 100%;
  padding-left: 1rem;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  .name {
    font-size: 1.4rem;
    font-weight: bold;
    color: ${props => props.theme.colors.dark_blue};
    position: relative;
    top: 0.5rem;
  }
`;

const FinishBtn = styled(Button)`
  width: 40%;
  margin-top: 2rem;
`;
