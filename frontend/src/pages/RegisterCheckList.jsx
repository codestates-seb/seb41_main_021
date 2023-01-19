import styled from 'styled-components';
import Header from '../components/Header';
import Navbar from '../components/NavBar';
import Button from '../components/common/Button';
import RegisterListView from '../components/registerList/RegisterListView';

export default function RegisterCheckList() {
  return (
    <>
      <Header title="RegisterCheckList" />
      <Content>
        <RegisterListView />
        <ButtonContainer>
          <RejectButton>거절하기</RejectButton>
          <Button>수락하기</Button>
        </ButtonContainer>
      </Content>
      <Navbar />
    </>
  );
}

const Content = styled.div`
  flex: 1;
  overflow: scroll;
  display: flex;
  flex-direction: column;
  align-items: center;

  button {
    margin-top: 2rem;
  }
`;

const RejectButton = styled(Button)`
  margin-right: 1rem;
  background: ${props => props.theme.colors.gray};
  &:hover {
    background: ${props => props.theme.colors.light_gray};
  }
  &:active {
    background: ${props => props.theme.colors.dark_gray};
  }
`;

const ButtonContainer = styled.div``;
