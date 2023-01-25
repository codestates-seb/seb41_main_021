import styled from 'styled-components';
import Button from '../components/common/Button';
import NavBar from '../components/NavBar';
import Header from '../components/Header';
import ProfileContainer from '../components/Tayo/ProfileContainer';
import InfoContainer from '../components/Tayo/InfoContainer';
import MemberContainer from '../components/Tayo/MemberContainer';
import { useTayoGet } from '../hooks/useTayo';

export default function TabnidaDetail() {
  return (
    <>
      <Header title={'탑니다'}></Header>
      <Container>
        <ProfileContainer />
        <InfoContainer state="가능" />
        <MemberContainer />
        <InviteButton>초대하기</InviteButton>
      </Container>
      <NavBar />
    </>
  );
}

const Container = styled.div`
  flex: 1;
  overflow: scroll;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const InviteButton = styled(Button)`
  width: 40%;
  margin-top: 2.5rem;
`;
