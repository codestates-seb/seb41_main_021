import styled from 'styled-components';
import Header from '../components/Header';
import Navbar from '../components/NavBar';
import RegisterListView from '../components/registerList/RegisterListView';

export default function RegisterCheckList() {
  return (
    <>
      <Header title="" />
      <Content>
        <RegisterListView />
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
