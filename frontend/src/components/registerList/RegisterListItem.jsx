import { useState } from 'react';
import styled, { css } from 'styled-components';
import { MdDone, MdDelete } from 'react-icons/md';

const RegisterListItem = props => {
  // api 응답 어떻게 올지 몰라서 대충 넣어놓음
  const { date, userInfo, transit, onClick } = props;
  const [check, setCheck] = useState(false);

  return (
    <>
      {/* 컨테이너 클릭 시 해당 유저 페이지로 이동 */}
      <Container onClick={onClick}>
        <TextContainer>
          {/* 요청받은 날짜 */}
          <DateContainer>{date}</DateContainer>
          <UserInfoContainer>
            <UserInfoText check={check}>{userInfo}</UserInfoText>
            <CheckCircle onClick={() => setCheck(!check)} check={check}>
              {check && <MdDone />}
            </CheckCircle>
          </UserInfoContainer>
          <TransitContainer>경유 {transit}회</TransitContainer>
        </TextContainer>
      </Container>
    </>
  );
};

const Container = styled.div`
  width: 100%;
  height: 9rem;
  border-bottom: 1px solid ${props => props.theme.colors.light_gray};

  @media only screen and (min-width: 800px) {
  }
`;

const TextContainer = styled.div`
  width: 100%;
  height: 100%;
  padding: 1rem;
  display: flex;
  justify-content: center;
  flex-direction: column;
`;

const DateContainer = styled.div`
  display: flex;
  align-items: center;
  color: ${props => props.theme.colors.gray};
  font-weight: bold;
  margin-bottom: 0.5rem;
`;

const UserInfoContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1rem;
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

const CheckCircle = styled.div`
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 2.5rem;
  border: 1px solid #ced4da;
  font-size: 1.7rem;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 1.5rem;
  cursor: pointer;
  ${props =>
    props.check &&
    css`
      border: 1px solid ${props => props.theme.colors.darker_blue};
      color: ${props => props.theme.colors.darker_blue};
    `}
`;
const TransitContainer = styled.div`
  display: flex;
  align-items: center;
`;

export default RegisterListItem;
