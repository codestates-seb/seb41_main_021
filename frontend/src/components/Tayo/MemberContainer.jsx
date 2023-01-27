import styled from 'styled-components';
import MemberListItem from './MemberListItem';
import { useEffect } from 'react';
import { useState } from 'react';
import useGetData from '../../hooks/useGetData';

export default function MemberContainer(props) {
  const { yataId } = props;

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    useGetData(`https://server.yata.kro.kr/api/v1/yata/${yataId}/accept/yataRequests`).then(res => {
      setData(res.data.data, setLoading(false));
    });
  }, []);

  return (
    <>
      {data.length !== 0 && !loading && (
        <ContentContainer>
          <h2>확정된 탑승자</h2>
          {data.map(el => {
            return <MemberListItem key={el.yataId} nickname={el.nickname} state={el.yataPaid} />;
          })}
        </ContentContainer>
      )}
    </>
  );
}

const ContentContainer = styled.div`
  width: 90%;
  margin: 1rem 0;

  h2 {
    font-size: 1.2rem;
    margin-bottom: 0.2rem;
  }
`;
