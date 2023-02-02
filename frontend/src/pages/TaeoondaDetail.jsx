import { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import styled from 'styled-components';
import Button from '../components/common/Button';
import NavBar from '../components/NavBar';
import Header from '../components/Header';
import ProfileContainer from '../components/Tayo/ProfileContainer';
import InfoContainer from '../components/Tayo/InfoContainer';
import MemberContainer from '../components/Tayo/MemberContainer';
import EditDeleteContainer from '../components/Tayo/EditDeleteContainer';
import { useGetData } from '../hooks/useGetData';
import { useTayoRequest } from '../hooks/useTayo';
import { useSelector } from 'react-redux';
import Modal from '../components/common/Modal';

export default function TabnidaDetail() {
  const params = useParams();
  const yataId = params.yataId;

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [count, setCount] = useState(1);
  const [show, setShow] = useState(false);

  const updateValue = e => {
    const newCount = e.target.value;
    setCount(newCount);
  };

  const email = useSelector(state => {
    return state.user.email;
  });

  const requestHandler = () => {
    const newData = {
      title: data.title,
      specifics: data.specifics,
      departureTime: data.departureTime,
      timeOfArrival: data.timeOfArrival,
      boardingPersonCount: count,
      maxWaitingTime: data.maxWaitingTime,
      strPoint: {
        longitude: data.strPoint.longitude,
        latitude: data.strPoint.latitude,
        address: data.strPoint.address,
      },
      destination: {
        longitude: data.destination.longitude,
        latitude: data.destination.latitude,
        address: data.destination.address,
      },
    };

    useTayoRequest(`/api/v1/yata/apply/${yataId}`, newData).then(res => {
      console.log(res);
    });
    setShow(false);
  };

  useEffect(() => {
    useGetData(`/api/v1/yata/${yataId}`).then(res => setData(res.data.data, setLoading(false)));
  }, []);

  return (
    <>
      {loading || (
        <>
          <Header title={'태웁니다'}></Header>
          <Container>
            {data.email === email && (
              <EditDeleteContainer
                state={'tabnida'}
                yataId={yataId}
                data={data}
                ableTag={'초대 가능'}
                disableTag={'초대 마감'}
              />
            )}
            <ProfileContainer data={data} />
            <InfoContainer data={data} />
            <MemberContainer data={data} email={email} />
            <InviteButton
              onClick={() => {
                setShow(true);
              }}>
              신청하기
            </InviteButton>
            <Modal show={show} onClose={() => setShow(false)} event={requestHandler} submitText="신청하기">
              <ModalForm>
                <ModalHeader>탑승 인원</ModalHeader>
                <ModalDesc>탑승 예정 인원을 입력해주세요.</ModalDesc>
                <ModalCounter>
                  <ModalInput type="number" min="1" max="10" placeholder="1" onChange={updateValue} />
                </ModalCounter>
              </ModalForm>
            </Modal>
          </Container>
          <NavBar />
        </>
      )}
    </>
  );
}

const Container = styled.div`
  flex: 1;
  overflow: scroll;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const InviteButton = styled(Button)`
  width: 40%;
  margin: 2.5rem 0;
`;
const ModalForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;

  /* 
  input::-webkit-outer-spin-button,
  input::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  } */
`;

const ModalHeader = styled.h1`
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
`;

const ModalDesc = styled.div``;

const ModalCounter = styled.div`
  display: flex;
  align-items: flex-end;
  margin-top: 2rem;
`;

const ModalInput = styled.input`
  margin-left: 2.8rem;
  font-size: 2rem;
  outline: none;
`;
