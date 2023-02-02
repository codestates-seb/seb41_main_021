import { useState } from 'react';
import styled, { css } from 'styled-components';
import Button from '../common/Button';
import usePostData from '../../hooks/usePostData';
import { useEffect } from 'react';
import { useGetData } from '../../hooks/useGetData';
import useDeleteData from '../../hooks/useDeleteData';
import { TiDelete } from 'react-icons/ti';

const RegisterListItem = props => {
  // api 응답 어떻게 올지 몰라서 대충 넣어놓음
  const { date, userInfo, yataId, yataRequestId, state, onClick, update, setUpdate, RequestStatus } = props;
  const [check, setCheck] = useState(false);

  const deleteItem = () => {
    useDeleteData(`/api/v1/yata/requests/${yataId}/${yataRequestId}`).then(res => {
      setUpdate(true);
    });
  };
  console.log(RequestStatus === 'INVITE');

  return (
    <>
      {RequestStatus === 'INVITE' && (
        <Container>
          <DeleteContainer>
            <TiDelete onClick={deleteItem} />
          </DeleteContainer>
          <TextContainer>
            <DateContainer>{date}</DateContainer>
            <UserInfoContainer>
              <UserInfoText check={check}>{userInfo}</UserInfoText>
              {state && (
                <TagContainer state={state}>
                  {state === 'NOT_YET' ? '승인 대기' : state === 'ACCEPTED' ? '승인 확정' : '승인 거절'}
                </TagContainer>
              )}
            </UserInfoContainer>
          </TextContainer>
        </Container>
      )}
    </>
  );
};
const DeleteContainer = styled.div`
  font-size: 2rem;
  &:hover {
    color: #ff6b6b;
  }
  display: none;
`;
const Container = styled.div`
  width: 100%;
  padding: 1rem;
  border-bottom: 1px solid #f6f6f6;
  display: flex;
  align-items: center;
  justify-content: center;
  &:hover {
    ${DeleteContainer} {
      display: initial;
    }
  }
`;

const TextContainer = styled.div`
  flex: 1;
  height: 100%;
  padding: 1rem;
  display: flex;
  flex-direction: column;
`;

const DateContainer = styled.div`
  display: flex;
  flex-direction: column;
  color: ${props => props.theme.colors.gray};
  font-weight: bold;
  margin-bottom: 0.5rem;
`;

const UserInfoContainer = styled.div`
  display: flex;
  align-items: center;
`;

const UserInfoText = styled.span`
  display: flex;
  align-items: center;
  font-size: 1.5rem;
  font-weight: bold;
  ${props =>
    props.check &&
    css`
      color: ${props => props.theme.colors.gray};
    `}
`;

const ButtonContainer = styled.div`
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;

  button {
    margin-top: 0;
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

  background-color: ${props =>
    props.state === 'NOT_YET'
      ? props.theme.colors.gray
      : props.state === 'ACCEPTED'
      ? props.theme.colors.main_blue
      : props.theme.colors.light_red};
`;

const InviteOrRequestText = styled.div`
  font-size: 0.9rem;
  margin-bottom: 0.2rem;
  color: #bf89f0;
`;

export default RegisterListItem;
