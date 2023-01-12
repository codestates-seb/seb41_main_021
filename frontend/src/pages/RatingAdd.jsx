import styled from 'styled-components';
import NavBar from '../components/NavBar';
import Header from '../components/Header';
import { MdOutlineAccountBox } from 'react-icons/md';
import Button from '../components/common/Button';

export default function RatingAdd(props) {
  return (
    <>
      <NavBar></NavBar>
      <Container>
        <Header title={'매너 평가'}></Header>
        <ProfileCotainer>
          <Profile>
            <MdOutlineAccountBox />
            <Info>
              <div className="name">닉네임</div>
              <div className="date">2023.01.11 (수) </div>
            </Info>
          </Profile>
        </ProfileCotainer>
        <RatingContainer>
          <TitleContainer>
            <div className="title"> 👍 좋았던 점을 선택해 주세요 !</div>
          </TitleContainer>
          <ListContainer>
            <Item>운전을 잘해요</Item>
            <Item>친절하고 매너가 좋아요</Item>
            <Item>깔끔해요</Item>
            <Item>시간 약속을 잘 지켜요</Item>
            <Item>응답이 빨라요</Item>
          </ListContainer>
        </RatingContainer>
        <RatingContainer>
          <TitleContainer>
            <div className="title"> 👎 아쉬웠던 점을 선택해 주세요 !</div>
          </TitleContainer>
          <ListContainer>
            <Item>운전이 미숙해요</Item>
            <Item>불친절해요</Item>
            <Item>청결하지 않아요</Item>
            <Item>약속을 안 지켜요</Item>
            <Item>응답이 느려요</Item>
          </ListContainer>
        </RatingContainer>
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
const RatingContainer = styled.div`
  width: 100%;
  height: 30%;
  padding: 1rem 2rem 1rem 2rem;
  margin-top: 1rem;
  box-shadow: 0px -2px 1px -2px ${props => props.theme.colors.dark_gray};
`;
const TitleContainer = styled.div`
  width: 100%;
  margin-top: 1rem;
  display: flex;
  justify-content: center;
  .title {
    font-size: 1.3rem;
    color: ${props => props.theme.colors.dark_blue};
    font-weight: bold;
  }
`;
const ListContainer = styled.div`
  width: 100%;
  height: 100%;
  padding: 1rem;
`;
const Item = styled.div`
  width: 100%;
  height: 20%;
  display: flex;
  align-items: center;
  font-size: 1.1rem;
`;
const FinishBtn = styled(Button)`
  width: 40%;
  margin-top: 2rem;
`;
