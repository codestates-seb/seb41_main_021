import styled from 'styled-components';

import InviteListItem from './InviteListItem';
import { useState, useEffect } from 'react';
import { dateFormat } from '../common/DateFormat';
import { useGetData } from '../../hooks/useGetData';
import { useParams } from 'react-router';
import { FcFeedback } from 'react-icons/fc';

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
    <>
      {list.length !== 0 ? (
        <RegisterBlock>
          {/* <InfoContainer>
        <FcFeedback />
        내가 보낸 초대
      </InfoContainer> */}
          {list.map(el => {
            return (
              <InviteListItem
                key={el.yataId}
                date={dateFormat(el.createdAt)}
                userInfo={el.nickname}
                yataId={el.yataId}
                yataRequestId={el.yataRequestId}
                state={el.approvalStatus}
                RequestStatus={el.yataRequestStatus}
                update={update}
                setUpdate={setUpdate}></InviteListItem>
            );
          })}
        </RegisterBlock>
      ) : (
        <RegisterBlock>
          <div className="no-content">받은 신청 내역이 없습니다.</div>
        </RegisterBlock>
      )}
    </>
  );
};

const RegisterBlock = styled.div`
  width: 100%;
  height: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: scroll;
`;

const InfoContainer = styled.div`
  width: 95%;
  height: 1.5rem;
  background-color: #e9e4ff;
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
