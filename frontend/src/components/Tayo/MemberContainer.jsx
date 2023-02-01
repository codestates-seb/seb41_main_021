import styled from 'styled-components';
import MemberListItem from './MemberListItem';
import { useEffect } from 'react';
import { useState } from 'react';
import { useGetData } from '../../hooks/useGetData';

export default function MemberContainer({ data, email }) {
  const [newdata, setnewData] = useState([]);
  const [loading, setLoading] = useState(true);

  // useEffect(() => {
  //   useGetData(`api/v1/yata/${data.yataId}/accept/yataRequests`).then(res => {
  //     if (res.request.status === 200) {
  //       setnewData(res.data.data);
  //       setLoading(false);
  //     }
  //   });
  // }, []);

  return (
    <>
      <ContentContainer>
        <h2>
          확정된 탑승자 ({data.yataMembers.length} / {data.maxPeople})
        </h2>
        {data.yataMembers.length === 0 ? (
          <>{<div className="current-member">현재 확정된 탑승자가 없습니다.</div>}</>
        ) : (
          data.email === email &&
          data.yataMembers.map(el => {
            console.log(el);
            return (
              <>
                <MemberListItem
                  key={el.yataId}
                  yataId={el.yataId}
                  yataMemberId={el.yataMemberId}
                  email={el.email}
                  nickname={el.nickname}
                  state={el.yataPaid}
                />
              </>
            );
          })
        )}
      </ContentContainer>
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

  .current-member {
    display: flex;
    height: 100%;
    align-items: center;
  }
`;
