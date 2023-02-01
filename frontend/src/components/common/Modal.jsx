import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Button from '../common/Button';
import { FiX } from 'react-icons/fi';
import axios from 'axios';

const Modal = props => {
  const { title, children, event, isUpload } = props;

  const [files, setFiles] = useState('');

  const onLoadFile = e => {
    const file = e.target.files;
    console.log(file);
    setFiles(file);
  };

  const handleClick = e => {
    e.preventDefault();
    const formdata = new FormData();
    formdata.append('file', files[0]);
    console.log(files[0]);

    axios
      .post('https://server.yata.kro.kr/api/v1/images/profile', formdata, {
        headers: {
          'Content-Type': 'multipart/form-data;charset=UTF-8; boundary=6o2knFse3p53ty9dmcQvWAIx1zInP11uCfbm',
          Authorization: localStorage.getItem('ACCESS'),
        },
      })
      .then(res => {
        console.log(res);
        window.location.reload();
      });
  };

  if (!props.show) {
    return null;
  }
  return (
    <>
      <ModalContainer onClick={props.onClose}>
        <ModalContent onClick={e => e.stopPropagation()}>
          <ModalHeader>
            <FiX onClick={props.onClose}>취소</FiX>
            <ModalTitle>{title}</ModalTitle>
          </ModalHeader>
          {isUpload ? (
            <>
              <ModalBody>
                <ModalText>
                  <form>
                    <label htmlFor="image"></label>
                    <input type="file" id="image" accept="img/*" onChange={onLoadFile} multiple />
                  </form>
                </ModalText>
              </ModalBody>
              <ButtonContainer>
                <Button onClick={handleClick}>저장하기</Button>
              </ButtonContainer>
            </>
          ) : (
            <>
              <ModalBody>
                <ModalText>{children}</ModalText>
              </ModalBody>
              <ButtonContainer>
                <Button onClick={event}>결제하기</Button>
              </ButtonContainer>
            </>
          )}
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
  width: 60%;

  background-color: #ffffff;
  border-radius: 1rem;

  box-shadow: 0px 0px 1px gray;
  overflow: scroll;

  @media only screen and (min-width: 800px) {
    width: auto;
  }
`;

const ModalHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;

  svg {
    align-self: flex-end;
    margin: 1rem;
    cursor: pointer;
  }
`;

const ModalTitle = styled.h1`
  font-size: 1.5rem;
`;

const ModalBody = styled.div`
  margin: 2rem;
  padding: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const ModalText = styled.div`
  font-size: 1.2rem;
`;

const ButtonContainer = styled.div`
  margin-bottom: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export default Modal;
