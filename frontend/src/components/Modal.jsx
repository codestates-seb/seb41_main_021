import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { useTayoInvite } from '../hooks/useTayo';
import Button from './common/Button';
import { FiX } from 'react-icons/fi';
import { dateFormat } from './common/DateFormat';

const Modal = props => {
  const { data, modalData } = props;

  const requestHandler = () => {
    const newData = {
      inviteEmail: data.email,
      yataId: data.yataId,
    };

    useTayoInvite(`https://server.yata.kro.kr/api/v1/yata/invite`, newData).then(res => {
      console.log(newData);
      console.log(res);
    });
  };

  if (!props.show) {
    return null;
  }

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
      <ModalContainer onClick={props.onClose}>
        <ModalContent onClick={e => e.stopPropagation()}>
          <ModalHeader>
            <ModalTitle>나의 태웁니다 게시글</ModalTitle>
            <FiX onClick={props.onClose}>취소</FiX>
          </ModalHeader>
          {modalData.map(el => {
            return (
              <>
                <ModalBody key={el.yataId}>
                  <InfoContainer>
                    <JourneyContainer>
                      <div>출발지: {shortWords(el.strPoint.address)}</div>
                      <div>도착지: {shortWords(el.destination.address)}</div>
                    </JourneyContainer>
                    <PeopleContainer>
                      인원: {el.reservedMemberNum} / {el.maxPeople}
                    </PeopleContainer>
                    <DateContainer>시간: {dateFormat(el.departureTime)}</DateContainer>
                  </InfoContainer>
                  <InviteBtn onClick={requestHandler}>선택하기</InviteBtn>
                </ModalBody>
              </>
            );
          })}
        </ModalContent>
      </ModalContainer>
    </>
  );
};

const ModalContainer = styled.div`
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  backdrop-filter: brightness(0.7);
  display: flex;
  align-items: center;
  justify-content: center;
`;

const ModalContent = styled.div`
  width: 90%;
  height: 85%;
  background-color: #ffffff;
  border-radius: 1rem;
  opacity: 100%;
  box-shadow: 0px 0px 1px gray;
  @media only screen and (min-width: 800px) {
    width: 430px;
    height: 790px;
  }
`;

const ModalHeader = styled.div`
  height: 4rem;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid #dddddd;
  svg {
    position: absolute;
    font-size: 1.7rem;
    cursor: pointer;
    margin-left: 27rem;
    @media only screen and (min-width: 768px) {
      margin-left: 32rem;
    }
  }
`;

const ModalTitle = styled.h1`
  font-size: 1.2rem;
`;

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

export default Modal;
