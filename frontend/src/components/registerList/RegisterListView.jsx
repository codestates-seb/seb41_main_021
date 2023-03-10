import styled from 'styled-components';
import RegisterListItem from './RegisterListItem';
import { useState, useEffect } from 'react';
import { dateFormat } from '../common/DateFormat';
import { useGetData } from '../../hooks/useGetData';
import { useParams } from 'react-router';
import { FcInspection } from 'react-icons/fc';

const RegisterListView = () => {
  const params = useParams();
  const yataId = params.yataId;
  const [list, setList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [update, setUpdate] = useState(true);

  useEffect(() => {
    if (update) {
      useGetData(`api/v1/yata/requests/${yataId}`).then(res => {
        setList(res.data.data);
        setUpdate(!update);
        setLoading(false);
      });
    }
  }, [update]);

  return (
    <RegisterBlock>
      {/* <InfoContainer>
        <FcInspection />이 게시물에 온 신청
      </InfoContainer> */}

      {list.map(el => {
        return (
          <RegisterListItem
            key={el.yataId}
            date={dateFormat(el.createdAt)}
            userInfo={el.nickname}
            yataId={el.yataId}
            yataRequestId={el.yataRequestId}
            state={el.approvalStatus}
            RequestStatus={el.yataRequestStatus}
            update={update}
            setUpdate={setUpdate}
            boardingPersonCount={el.boardingPersonCount}></RegisterListItem>
        );
      })}
    </RegisterBlock>
  );
};

const RegisterBlock = styled.div`
  width: 100%;

  display: flex;
  flex-direction: column;
  align-items: center;
`;

const InfoContainer = styled.div`
  width: 95%;
  height: 1.5rem;
  background-color: #c7daff;
  color: black;

  font-size: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;

  padding: 0.2rem;
  border-radius: 0.5rem;
  margin-top: 1rem;

  svg {
    font-size: 1.1rem;
    margin-right: 0.3rem;
  }
`;

export default RegisterListView;
