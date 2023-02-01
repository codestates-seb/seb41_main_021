import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Button from '../common/Button';
import { FiX } from 'react-icons/fi';
import InviteModalItem from './InviteModalItem';
import { useGetData } from '../../hooks/useGetData';

const Modal = props => {
  const { data } = props;

  const [modalData, setModalData] = useState([]);

  useEffect(() => {
    useGetData(`/api/v1/yata/myYatas/neota/notClosed`).then(res => {
      setModalData(res.data.data);
    });
  }, []);

  if (!props.show) {
    return null;
  }
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
              <InviteModalItem
                key={data.yataId}
                yataId={el.yataId}
                invitedYataId={data.yataId}
                inviteEmail={data.email}
                strPoint={el.strPoint.address}
                destination={el.destination.address}
                resNum={el.reservedMemberNum}
                maxPeople={el.maxPeople}
                departureTime={el.departureTime}
              />
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
  overflow: scroll;

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

export default Modal;
