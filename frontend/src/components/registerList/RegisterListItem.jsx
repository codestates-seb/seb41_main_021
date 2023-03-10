import { useState } from 'react';
import styled, { css } from 'styled-components';
import Button from '../common/Button';
import usePostData from '../../hooks/usePostData';
import { BsPeople } from 'react-icons/bs';
import { toast } from 'react-toastify';

const RegisterListItem = props => {
  // api 응답 어떻게 올지 몰라서 대충 넣어놓음
  const {
    date,
    userInfo,
    yataId,
    yataRequestId,
    state,
    onClick,
    update,
    setUpdate,
    RequestStatus,
    boardingPersonCount,
  } = props;
  const [check, setCheck] = useState(false);

  const approveHandler = () => {
    const data = {};
    usePostData(`/api/v1/yata/${yataId}/${yataRequestId}/accept`, data).then(res => {
      if (res.status === 204) {
        toast.success('수락 완료');
        setUpdate(true);
      } else {
        toast.warning('수락 실패');
      }
    });
  };

  const rejectHandler = () => {
    const data = {};
    usePostData(`/api/v1/yata/${yataId}/${yataRequestId}/reject`, data).then(res => {
      if (res.status === 204) {
        toast.success('거절 완료.');
        setUpdate(true);
      } else {
        toast.warning('거절 실패');
      }
    });
  };

  return (
    <>
      {RequestStatus === 'APPLY' && (
        <Container>
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
            <PeopleContainer>
              <BsPeople /> 신청 인원: {boardingPersonCount}명
            </PeopleContainer>
          </TextContainer>
          <ButtonContainer>
            <RejectButton onClick={rejectHandler}>거절하기</RejectButton>
            <Button onClick={approveHandler}>수락하기</Button>
          </ButtonContainer>
        </Container>
      )}
    </>
  );
};

const Container = styled.div`
  width: 100%;
  padding: 1rem;
  border-bottom: 1px solid #f6f6f6;
  display: flex;
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

const PeopleContainer = styled.div`
  display: flex;
  margin-top: 0.5rem;

  color: ${props => props.theme.colors.dark_gray};
  svg {
    font-size: 1.1rem;
    margin-right: 0.2rem;
  }
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

export default RegisterListItem;
