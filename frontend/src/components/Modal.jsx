import { useState, useEffect } from 'react';
import styled from 'styled-components';
import { useTayoInvite } from '../hooks/useTayo';
import Button from './common/Button';

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
  return (
    <>
      <ModalContainer onClick={props.onClose}>
        <ModalContent onClick={e => e.stopPropagation()}>
          <ModalHeader />
          <ModalTitle>나의 태웁니다 게시글</ModalTitle>
          {modalData.map(el => {
            return (
              <>
                <ModalBody key={el.yataId}>
                  <JourneyContainer>
                    {el.strPoint.address} > {el.destination.address}
                  </JourneyContainer>
                  <PeopleContainer>
                    {el.reservedMemberNum} / {el.maxPeople}
                  </PeopleContainer>
                  <InviteBtn onClick={requestHandler}>선택하기</InviteBtn>
                </ModalBody>
              </>
            );
          })}
          <ModalFooter>
            <CloseBtn onClick={props.onClose}>취소</CloseBtn>
          </ModalFooter>
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

  display: flex;
  align-items: center;
  justify-content: center;
`;

const ModalContent = styled.div`
  width: 90%;
  height: 90%;
  background-color: #fff;
  background-color: #eaeaea;
  border-radius: 1rem;
  opacity: 100%;
`;

const ModalHeader = styled.div`
  padding: 10px;
`;

const ModalTitle = styled.h1`
  display: flex;
  align-items: center;
  justify-content: center;
`;

const ModalBody = styled.div`
  padding: 10px;
  border-top: 1px solid #eee;
  border-bottom: 1px solid #eee;
`;

const ModalFooter = styled.div`
  padding: 10px;
`;

const CloseBtn = styled.button``;

const InviteBtn = styled(Button)``;

const JourneyContainer = styled.div``;

const PeopleContainer = styled.div``;

export default Modal;
