import styled from 'styled-components';
import MemberListItem from './MemberListItem';
import { useEffect } from 'react';
import { useState } from 'react';
import useGetData from '../../hooks/useGetData';

export default function MemberContainer(props) {
  const { data } = props;

  return (
    <>
      <ContentContainer>
        <h2>
          확정된 탑승자 {data.reservedMemberNum}/{data.maxPeople}
        </h2>
        {data.reservedMemberNum === 0 ? (
          <div className="current-member">현재 확정된 탑승자가 없습니다.</div>
        ) : (
          data.yataMembers.map(el => {
            return (
              <MemberListItem
                key={el.yataId}
                yataId={el.yataId}
                yataMemberId={el.yataMemberId}
                email={el.email}
                nickname={el.nickname}
                state={el.yataPaid}
              />
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
