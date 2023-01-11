import styled from 'styled-components';
import RegisterListItem from './RegisterListItem';

const RegisterListView = () => {
  return (
    <RegisterBlock>
      <RegisterListItem date={'1월 3일 (화) 7:00PM'} userInfo="이이잉" transit="1"></RegisterListItem>
      <RegisterListItem date={'1월 4일 (수) 7:00PM'} userInfo="하잉" transit="1"></RegisterListItem>
    </RegisterBlock>
  );
};

const RegisterBlock = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export default RegisterListView;
