import styled from 'styled-components';
import Header from '../components/Header';
import Navbar from '../components/NavBar';
import RegisterListView from '../components/registerList/RegisterListView';
import InviteListView from '../components/registerList/InviteListView';
import { useState } from 'react';

export default function RegisterCheckList() {
  const [defaultTab, setDefaultTab] = useState(true);

  return (
    <>
      {defaultTab ? <Header title="내가 받은 신청" /> : <Header title="내가 보낸 초대" />}
      <Content>
        <TabContainer>
          <Menu
            onClick={() => {
              setDefaultTab(true);
            }}
            defaultTab={defaultTab}>
            내가 받은 신청
          </Menu>
          <Menu
            onClick={() => {
              setDefaultTab(false);
            }}
            defaultTab={!defaultTab}>
            내가 보낸 초대
          </Menu>
        </TabContainer>
        {defaultTab ? <RegisterListView /> : <InviteListView />}
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

const TabContainer = styled.div`
  width: 100%;
  height: 3rem;

  display: flex;
  align-items: center;
  justify-content: space-around;
`;

const Menu = styled.div`
  margin: 0;
  height: 3rem;
  width: 100%;
  font-size: 1.2rem;

  display: flex;
  align-items: center;
  justify-content: center;

  color: ${props => props.defaultTab && '#73b2d9'};
  border-bottom: ${props => props.defaultTab && '2px solid #73b2d9'};

  &:hover {
    background-color: #73b2d9;
    color: white;
  }

  /* 
  :not(:last-of-type) {
    border-right: 1px solid black;
  } */
`;
