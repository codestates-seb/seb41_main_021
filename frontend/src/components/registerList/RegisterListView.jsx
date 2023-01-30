import styled from 'styled-components';
import RegisterListItem from './RegisterListItem';
import { useState, useEffect } from 'react';
import { dateFormat } from '../common/DateFormat';
import { useGetData } from '../../hooks/useGetData';
import { useParams } from 'react-router';

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
      {list.map(el => {
        return (
          <RegisterListItem
            key={el.yataId}
            date={dateFormat(el.createdAt)}
            userInfo={el.nickname}
            yataId={el.yataId}
            yataRequestId={el.yataRequestId}
            state={el.approvalStatus}
            update={update}
            setUpdate={setUpdate}></RegisterListItem>
        );
      })}
    </RegisterBlock>
  );
};

const RegisterBlock = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export default RegisterListView;
