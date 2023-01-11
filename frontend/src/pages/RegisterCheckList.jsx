import styled from 'styled-components';
import Header from '../components/Header';
import Navbar from '../components/NavBar';
import Button from '../components/common/Button';
import RegisterListView from '../components/registerList/RegisterListView';

export default function RegisterCheckList() {
  return (
    <>
      <Header title="RegisterCheckList" />
      <Navbar />
      <Content>
        <RegisterListView />
        <Button>수락하기</Button>
      </Content>
    </>
  );
}

const Content = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;

  button {
    margin-top: 2rem;
  }
`;
