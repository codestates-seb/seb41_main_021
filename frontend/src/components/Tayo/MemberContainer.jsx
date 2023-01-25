import styled from 'styled-components';
import MemberListItem from './MemberListItem';

export default function MemberContainer(props) {
  const { data } = props;
  return (
    <ContentContainer>
      <h2>
        확정된 탑승자 {data.reservedMemberNum}/{data.maxPeople}
      </h2>
      {/* {data.reservedMemberNum.map(el => {
        <MemberListItem state="완료" />
      })} */}
      <MemberListItem state="완료" />
      <MemberListItem state="1" />
      <MemberListItem state="1" />
    </ContentContainer>
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
