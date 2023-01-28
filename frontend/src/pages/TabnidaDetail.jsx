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
import { useTayoInvite } from '../hooks/useTayo';

export default function TabnidaDetail() {
  const params = useParams();
  const yataId = params.yataId;

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  const requestHandler = () => {
    const newData = {
      inviteEmail: data.email,
      yataId: data.yataId,
      // title: data.title,
      // specifics: data.specifics,
      // departureTime: data.departureTime,
      // timeOfArrival: data.timeOfArrival,
      // boardingPersonCount: 1,
      // maxWaitingTime: data.maxWaitingTime,
      // strPoint: {
      //   longitude: data.strPoint.longitude,
      //   latitude: data.strPoint.latitude,
      //   address: data.strPoint.address,
      // },
      // destination: {
      //   longitude: data.destination.longitude,
      //   latitude: data.destination.latitude,
      //   address: data.destination.address,
      // },
    };

    useTayoInvite(`https://server.yata.kro.kr/api/v1/yata/invite`, newData).then(res => {
      console.log(res);
    });
  };

  useEffect(() => {
    useGetData(`https://server.yata.kro.kr/api/v1/yata/${yataId}`).then(res => {
      setData(res.data.data, setLoading(false));
    });
  }, []);

  return (
    <>
      {loading || (
        <>
          <Header title={'탑니다'}></Header>
          <Container>
            <EditDeleteContainer
              state={'tabnida'}
              yataId={yataId}
              data={data}
              ableTag={'초대 가능'}
              disableTag={'초대 마감'}
            />
            <ProfileContainer data={data} />
            <InfoContainer data={data} />
            <MemberContainer data={data} />
            <InviteButton onClick={requestHandler}>초대하기</InviteButton>
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
  margin-top: 2.5rem;
`;
