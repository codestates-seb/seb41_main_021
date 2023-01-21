import styled from 'styled-components';
import MemberListItem from './MemberListItem';

export default function MemberContainer() {
  return (
    <ContentContainer>
      <h2>확정된 탑승자 2/5</h2>
      <MemberListItem state="완료" />
      <MemberListItem state="dlksafjl" />
      <MemberListItem state="adkfhdas" />
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
