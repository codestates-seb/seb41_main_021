import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { useTayoInvite } from '../../hooks/useTayo';
import Button from '../common/Button';
import { dateFormat } from '../common/DateFormat';

const InviteModalItem = props => {
  const { yataId, invitedYataId, inviteEmail, strPoint, destination, resNum, maxPeople, departureTime } = props;

  const requestHandler = () => {
    const newData = {
      inviteEmail,
      yataId,
      invitedYataId,
    };

    useTayoInvite(`https://server.yata.kro.kr/api/v1/yata/invite`, newData);
  };

  const shortWords = (str, length = 19) => {
    let result = '';
    if (str.length > length) {
      result = str.substr(0, length) + '...';
    } else {
      result = str;
    }
    return result;
  };

  return (
    <>
      <ModalBody>
        <InfoContainer>
          <JourneyContainer>
            <div>출발지: {shortWords(strPoint)}</div>
            <div>도착지: {shortWords(destination)}</div>
          </JourneyContainer>
          <PeopleContainer>
            인원: {resNum} / {maxPeople}
          </PeopleContainer>
          <DateContainer>시간: {dateFormat(departureTime)}</DateContainer>
        </InfoContainer>
        <InviteBtn onClick={requestHandler}>선택하기</InviteBtn>
      </ModalBody>
    </>
  );
};

const ModalBody = styled.div`
  padding: 10px;
  border-bottom: 1px solid #dddddd;
  display: flex;
  align-items: center;
`;

const InfoContainer = styled.div`
  width: 80%;
`;

const InviteBtn = styled(Button)`
  height: 2.5rem;
`;

const JourneyContainer = styled.div`
  margin-bottom: 0.5rem;
  div {
    font-weight: bold;
    font-size: 1.2rem;
    padding: 0.3rem;
  }
`;

const PeopleContainer = styled.div`
  padding: 0.1rem 0 0.5rem 0.3rem;
`;

const DateContainer = styled.div`
  padding-left: 0.3rem;
`;

export default InviteModalItem;
