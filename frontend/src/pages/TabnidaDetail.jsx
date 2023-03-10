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
import InviteModal from '../components/Tayo/InviteModal';
import { useSelector } from 'react-redux';

export default function TabnidaDetail() {
  const params = useParams();
  const yataId = params.yataId;

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [show, setShow] = useState(false);
  const email = useSelector(state => {
    return state.user.email;
  });

  useEffect(() => {
    useGetData(`/api/v1/yata/${yataId}`).then(res => {
      setData(res.data.data);
      setLoading(false);
    });
  }, []);

  return (
    <>
      {loading || (
        <>
          <Header title={'탑니다'}></Header>
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
            {data.email === email && <MemberContainer data={data} />}
            <InviteButton
              onClick={() => {
                setShow(true);
              }}>
              초대하기
            </InviteButton>
            <InviteModal show={show} onClose={() => setShow(false)} data={data} />
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
