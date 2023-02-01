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

export default function TabnidaDetail() {
  const params = useParams();
  const yataId = params.yataId;

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const email = useSelector(state => {
    return state.user.email;
  });

  const requestHandler = () => {
    const newData = {
      title: data.title,
      specifics: data.specifics,
      departureTime: data.departureTime,
      timeOfArrival: data.timeOfArrival,
      boardingPersonCount: 1,
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
            <InviteButton onClick={requestHandler}>신청하기</InviteButton>
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
